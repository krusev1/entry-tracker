package com.example.SHORT.TERM.TRADING.APP.service;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SeleniumService {

    private final Map<String, CircularFifoQueue<BigDecimal>> priceMap = new HashMap<>();

    private final TelegramBotService telegramBotService;

    public SeleniumService(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    public void openPage(String tokenName, String url, String sellAmount, String entryAmount) throws InterruptedException {
        WebDriver webDriver1 = new ChromeDriver();
        webDriver1.get(url);
        WebElement sellAmountInput = webDriver1.findElement(By.name("fromValue"));
        sellAmountInput.sendKeys(sellAmount);

        WebDriverWait wait = new WebDriverWait(webDriver1, Duration.ofSeconds(5));
        WebElement priceSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".h-4.flex.space-x-1")));

        String script = "let backgroundDiv = document.querySelector('div.bg-v2-background-page'); " +
                "let h1Profit = document.createElement('h1'); " +
                "h1Profit.style.textAlign = 'center'; " +
                "h1Profit.style.fontSize = '3rem'; " +
                "h1Profit.id = 'h1Profit'; " +
                "backgroundDiv.insertBefore(h1Profit, backgroundDiv.childNodes[1]); ";

        JavascriptExecutor webDriver11 = (JavascriptExecutor) webDriver1;

        webDriver11.executeScript(script);

        List<WebElement> priceReloadCircles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("circle.fill-transparent")));

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);



        if (priceReloadCircles.size() >= 2){
            WebElement reloadPriceCircle = priceReloadCircles.get(1);

            scheduledExecutorService.scheduleAtFixedRate(() -> {
                reloadPriceCircle.click();
                WebElement toReceiveAmount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("toValue")));
                List<WebElement> priceSpanElements = priceSpan.findElements(By.cssSelector("span"));
                if (priceSpanElements.size() >= 2) {
                    WebElement currentPrice = priceSpanElements.get(1);
                    String currentPriceText = currentPrice.getText();
                    if (priceSpanElements.size() == 4){
                        currentPriceText = this.removeSubscriptNumbersAndNewLines(currentPriceText);
                    }
                    BigDecimal currentPriceBigDecimal = new BigDecimal(currentPriceText);
                    this.updatePriceMap(tokenName, currentPriceBigDecimal);

                    try {
                        this.priceChangerTracker(tokenName);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    double value = Double.parseDouble(toReceiveAmount.getAttribute("value"));
                    double profit = Math.round((value - Double.parseDouble(entryAmount)) * 100.0) / 100.0;
                    profitNotificationBackground(profit, (JavascriptExecutor) webDriver1);
                }
            }, 0, 5, TimeUnit.SECONDS);

        }

    }

    private static void profitNotificationBackground(double profit, JavascriptExecutor webDriver1) {

        String script;
        if (profit > 0 ){
            script = "let backgroundDiv = document.querySelector('div.bg-v2-background-page'); " +
                    "backgroundDiv.style.backgroundColor = 'green'; " +
                    "let h1Profit = document.getElementById('h1Profit'); " +
                    "h1Profit.textContent = arguments[0] + '$ profit'; ";
        }
        else {
            script = "let backgroundDiv = document.querySelector('div.bg-v2-background-page'); " +
                    "backgroundDiv.style.backgroundColor = 'rgb(28 41 54/var(--tw-bg-opacity))'; " +
                    "let h1Profit = document.getElementById('h1Profit'); " +
                    "h1Profit.textContent = ''; ";
        }
        webDriver1.executeScript(script, profit);
    }

    private String removeSubscriptNumbersAndNewLines(String currentPrice){

        currentPrice = currentPrice.replaceAll("\\n", "");


        String[] subscripts = {"₁", "₂", "₃", "₄", "₅", "₆", "₇", "₈", "₉"};

        for (int i = 0; i < subscripts.length; i++) {
            currentPrice = currentPrice.replaceAll(subscripts[i], "0".repeat(i + 1));
        }
        return currentPrice;

    }

    private void updatePriceMap(String tokenName, BigDecimal currentPrice){
        CircularFifoQueue<BigDecimal> priceList = this.priceMap.getOrDefault(tokenName, new CircularFifoQueue<>(60));
        priceList.add(currentPrice);
        this.priceMap.put(tokenName, priceList);
    }

    private void priceChangerTracker(String tokenName) throws UnsupportedEncodingException {


        CircularFifoQueue<BigDecimal> tokenPriceQueue = priceMap.get(tokenName);
        int tokenPricesCount = tokenPriceQueue.size();

        if (tokenPricesCount > 2){

            BigDecimal lastPrice = tokenPriceQueue.get(tokenPricesCount - 2);
            BigDecimal currentPrice = tokenPriceQueue.get(tokenPricesCount - 1);

            BigDecimal change = currentPrice.subtract(lastPrice);

            BigDecimal percentPriceChange = change.divide(lastPrice, RoundingMode.DOWN).multiply(BigDecimal.valueOf(100));
            if (percentPriceChange.compareTo(BigDecimal.valueOf(10)) > 0){
                telegramBotService.sendMessageToTelegram(tokenName + " 5 Sec PUMP: " + percentPriceChange + "% " + LocalTime.now());
                System.out.println("5 Sec PUMP: " + percentPriceChange + "% " + LocalTime.now());
            }
            if (percentPriceChange.compareTo(BigDecimal.valueOf(-10)) < 0){
                telegramBotService.sendMessageToTelegram(tokenName + " 5 Sec DUMP: " + percentPriceChange + "% " + LocalTime.now());
                System.out.println("5 Sec DUMP: " + percentPriceChange + "% " + LocalTime.now());
            }

        }
    }


}
