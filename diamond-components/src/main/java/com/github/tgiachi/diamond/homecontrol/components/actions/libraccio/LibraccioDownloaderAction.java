package com.github.tgiachi.diamond.homecontrol.components.actions.libraccio;

import com.github.tgiachi.diamond.homecontrol.api.annotations.ActionProcessor;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.actions.IDiamondActionProcessor;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@ActionProcessor(name = "libraccio")
public class LibraccioDownloaderAction implements IDiamondActionProcessor {

    private final ThreadPoolTaskExecutor threadPoolExecutor;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public LibraccioDownloaderAction(@Qualifier("actionsExecutor") Executor actionExecutor) {
        threadPoolExecutor = (ThreadPoolTaskExecutor) actionExecutor;
    }

    @Override
    public void execute() {
        threadPoolExecutor.submit(getLinksRunnable());
    }

    private Runnable getLinksRunnable() {
        return () -> {
            try {
                var doc = Jsoup.connect("https://www.libraccio.it/libri-usati").get();
                var links = doc.select(".items");
                var items = links.get(1);
                var itemsLinks = items.select(".item");
                var categories = itemsLinks.select(".label");
                for (var category : categories) {
                    var link = category.absUrl("href");
                    var categoryName = category.wholeText();
                    threadPoolExecutor.submit(getCategoryPage(categoryName, link));
                }

                logger.info("Links: {}", (long) categories.size());
            } catch (Exception ex) {
                logger.error("Error during get main page", ex);
            }
        };
    }

    private Runnable getCategoryPage(String name, String link) {
        return () -> {
            try {
                var page = Jsoup.connect(link).get();
                logger.info("Downloaded category: {}", name);

                var totals = page.select(".totals");
                var totalElement = totals.get(0);
                var cleanTotal = totalElement.text().replace("Titoli 1-25 di ", "").replace(" trovati.", "");

                var pageCount = Integer.parseInt(cleanTotal) / 25;

                logger.info("{} have {} pages", name, pageCount);
                for (int pageNum = 1; pageNum < pageCount; pageNum++) {
                    Thread.sleep(20000);
                    threadPoolExecutor.submit(downloadPageCategory(Integer.toString(pageNum), Integer.toString(pageCount), name, String.format("%s&PX=%s", link, pageNum)));
                }

            } catch (Exception ex) {
                logger.error("Error during download page: {}", name);
            }
        };
    }

    private Runnable downloadPageCategory(String pageNumber, String pageMax, String category, String link) {
        return () -> {
            try {
                var page = Jsoup.connect(link).get();
                logger.info("Downloaded page {}/{} {}", pageNumber, pageMax, category);
            } catch (Exception ex) {
                logger.error("Error during download page {} for {} url: {}", pageNumber, category, link, ex);
            }
        };
    }
}
