package newsApp.dao;

import io.cucumber.java.an.E;
import newsApp.model.Article;
import newsApp.model.ArticleContent;
import newsApp.services.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import newsApp.dao.JdbcArticleDao;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArticleContentDao implements ArticleContentDao {

    private final JdbcTemplate jdbcTemplate;

    private JdbcArticleDao articleDao;

    public JdbcArticleContentDao(JdbcTemplate jdbcTemplate, JdbcArticleDao articleDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.articleDao = articleDao;
    }

    @Override
    public void insertBusinessContent() {
        List<Article> articles = articleDao.getBusinessArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Investing.com")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraph = doc.select("div.WYSIWYG p:not(.MsoNormal), div.WYSIWYG h2, div.WYSIWYG ul");
                    StringBuilder content = new StringBuilder();

                    // Iterate over each element
                    for (Element tag : paragraph) {
                        // Retain each paragraph as HTML
                        String paragraphHtml = tag.outerHtml();
                        // Print or process each paragraph HTML as needed
                        if(!paragraphHtml.contains("Position added successfully to: ") && !paragraphHtml.contains("We encourage you to use comments to engage with other users,") && !paragraphHtml.contains("Only post material that’s relevant to the topic being discussed.") && !paragraphHtml.contains("the conversation, don’t trash it.") && !paragraphHtml.contains("directed at an author or another user. Racism, sexism and other forms of discrimination will not be tolerated.") && !paragraph.contains("Investing.com’s") &&
                                !paragraphHtml.contains(" <li><p class=\"MsoNormal\" style=\"margin: 0cm; text-indent: -18.0pt; line-height: normal; mso-list: l0 level1 lfo1; tab-stops: list 36.0pt;\"><!--[if !supportLists]--><span style=\"font-size: 10.0pt; font-family: Symbol; mso-fareast-font-family: Symbol; mso-bidi-font-family: Symbol; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\"><span style=\"font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; font-size: 7pt; line-height: normal; font-family: 'Times New Roman';\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span></span><strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">Enrich</span></strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">&nbsp;</span><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">the conversation, don’t trash it.</span></p></li>\n" +
                                        " <li><p class=\"MsoNormal\" style=\"margin: 0cm; text-indent: -18.0pt; line-height: normal; mso-list: l0 level1 lfo1; tab-stops: list 36.0pt;\"><!--[if !supportLists]--><span style=\"font-size: 10.0pt; font-family: Symbol; mso-fareast-font-family: Symbol; mso-bidi-font-family: Symbol; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\"><span style=\"font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; font-size: 7pt; line-height: normal; font-family: 'Times New Roman';\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span><!--[endif]--><strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">Stay focused and on track.</span></strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">&nbsp;Only post material that’s relevant to the topic being discussed.&nbsp;</span></p></li>\n" +
                                        " <li><p class=\"MsoNormal\" style=\"margin: 0cm; text-indent: -18.0pt; line-height: normal; mso-list: l0 level1 lfo1; tab-stops: list 36.0pt;\"><!--[if !supportLists]--><span style=\"font-size: 10.0pt; font-family: Symbol; mso-fareast-font-family: Symbol; mso-bidi-font-family: Symbol; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\"><span style=\"font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; font-size: 7pt; line-height: normal; font-family: 'Times New Roman';\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span><!--[endif]--><strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">Be respectful.</span></strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">&nbsp;Even negative opinions can be framed positively and diplomatically. <span style=\"border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-bidi-font-weight: bold;\">Avoid profanity, slander or personal attacks</span>&nbsp;directed at an author or another user. Racism, sexism and other forms of discrimination will not be tolerated.</span></p></li>\n" +
                                        " <li><strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">Use standard writing style.</span></strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">&nbsp;Include punctuation and upper and lower cases. Comments that are written in all caps and contain excessive use of symbols will be removed.</span></li>\n" +
                                        " <li><strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">NOTE</span></strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">: Spam and/or promotional messages and comments containing links will be removed. </span><span style=\"font-size: 10pt; line-height: 107%; font-family: Arial, sans-serif; color: #333333; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\" lang=\"EN-US\">Phone numbers, email addresses, links to personal or business websites, Skype/Telegram/WhatsApp etc. addresses (including links to groups) will also be removed; self-promotional material or business-related solicitations or PR (ie, contact me for signals/advice etc.), and/or any other comment that contains personal contact specifcs or advertising will be removed as well. In addition, any of the above-mentioned violations may result in suspension of your account.</span></li>\n" +
                                        " <li><strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">Doxxing. </span></strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA; mso-bidi-font-weight: bold;\" lang=\"EN-US\">We do not allow any sharing of private or personal contact or other information about any individual or organization. This will result in immediate suspension of the commentor and his or her account.</span></li>\n" +
                                        " <li><strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">Don’t monopolize the conversation.</span></strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">&nbsp;We appreciate passion and conviction, but we also strongly believe in giving everyone a chance to air their point of view. Therefore, in addition to civil interaction, we expect commenters to offer their opinions succinctly and thoughtfully, but not so repeatedly that others are annoyed or offended. If we receive complaints about individuals who take over a thread or forum, we reserve the right to ban them from the site, without recourse.</span></li>\n" +
                                        " <li><strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">Only English</span></strong><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">&nbsp;comments will be allowed.</span></li>\n" +
                                        " <li><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK; mso-bidi-language: AR-SA;\" lang=\"EN-US\">Any comment you publish, together with your investing.com profile, <strong>will be public</strong> on investing.com and <strong>may be indexed</strong> and available through third party search engines, such as Google.</span></li>\n" +
                                        "</ul>\n" +
                                        "<p class=\"MsoNormal\" style=\"margin: 0cm; text-indent: -18.0pt; line-height: normal; mso-list: l0 level1 lfo1; tab-stops: list 36.0pt;\"><!--[if !supportLists]--><span style=\"font-size: 10.0pt; font-family: Symbol; mso-fareast-font-family: Symbol; mso-bidi-font-family: Symbol; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\"><span style=\"font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; font-size: 7pt; line-height: normal; font-family: 'Times New Roman';\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span></span><strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">Enrich</span></strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">&nbsp;</span><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">the conversation, don’t trash it.</span></p>\n" +
                                        "<p class=\"MsoNormal\" style=\"margin: 0cm; text-indent: -18.0pt; line-height: normal; mso-list: l0 level1 lfo1; tab-stops: list 36.0pt;\"><!--[if !supportLists]--><span style=\"font-size: 10.0pt; font-family: Symbol; mso-fareast-font-family: Symbol; mso-bidi-font-family: Symbol; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\"><span style=\"font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; font-size: 7pt; line-height: normal; font-family: 'Times New Roman';\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span><!--[endif]--><strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">Stay focused and on track.</span></strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">&nbsp;Only post material that’s relevant to the topic being discussed.&nbsp;</span></p>\n" +
                                        "<p class=\"MsoNormal\" style=\"margin: 0cm; text-indent: -18.0pt; line-height: normal; mso-list: l0 level1 lfo1; tab-stops: list 36.0pt;\"><!--[if !supportLists]--><span style=\"font-size: 10.0pt; font-family: Symbol; mso-fareast-font-family: Symbol; mso-bidi-font-family: Symbol; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\"><span style=\"font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; font-size: 7pt; line-height: normal; font-family: 'Times New Roman';\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span><!--[endif]--><strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">Be respectful.</span></strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">&nbsp;Even negative opinions can be framed positively and diplomatically. <span style=\"border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-bidi-font-weight: bold;\">Avoid profanity, slander or personal attacks</span>&nbsp;directed at an author or another user. Racism, sexism and other forms of discrimination will not be tolerated.</span></p>" +
                                        "<p><span style=\"color: #333333; font-family: Arial, sans-serif; font-size: 10pt;\">We encourage you to use comments to engage with other users, share your perspective and ask questions of authors and each other. However, in order to maintain the high level of discourse we’ve all come to value and expect, please keep the following criteria in mind:&nbsp;</span>&nbsp;</p>\n" +
                                        "<p class=\"MsoNormal\" style=\"margin: 0cm; text-indent: -18.0pt; line-height: normal; mso-list: l0 level1 lfo1; tab-stops: list 36.0pt;\"><!--[if !supportLists]--><span style=\"font-size: 10.0pt; font-family: Symbol; mso-fareast-font-family: Symbol; mso-bidi-font-family: Symbol; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\"><span style=\"font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; font-size: 7pt; line-height: normal; font-family: 'Times New Roman';\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></span><!--[endif]--><strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; border: none windowtext 1.0pt; mso-border-alt: none windowtext 0cm; padding: 0cm; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">Stay focused and on track.</span></strong><span style=\"font-size: 10.0pt; font-family: 'Arial',sans-serif; mso-fareast-font-family: 'Times New Roman'; color: #333333; mso-ansi-language: EN-US; mso-fareast-language: UK;\" lang=\"EN-US\">&nbsp;Only post material that’s relevant to the topic being discussed.&nbsp;</span></p>\n" +
                                        "<p class=\"MsoNormal\"><span style=\"font-size: 10.0pt; line-height: 107%; font-family: 'Arial',sans-serif; color: #333333; background: white; mso-ansi-language: EN-US;\" lang=\"EN-US\">Perpetrators of spam or abuse will be deleted from the site and prohibited from future registration at Investing.com’s discretion.</span></p>")
                        ) {
                            content.append(paragraphHtml);
                            content.append(System.lineSeparator());
                        }
                    }

                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Wall Street Journal")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select("p");
                    StringBuilder content = new StringBuilder();
                    content.append("You need to Subscribe to the WSJ in order to view this content. If you have a subscription feel free to click the link above to read the article, and make sure to come right back and drop a comment down below!" + System.lineSeparator() + System.lineSeparator());
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("MarketWatch")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraph = doc.select("div.article__body > div:not(.barrons-article-ad-wrapper, .barrons-main-article-ad-target, .barrons-article-ad-wrap, .media-object-article-reader)");

                    // Remove elements with class .audioplayer
                    paragraph.select(".audioplayer").remove();
                    paragraph.select("strong").remove();
                    paragraph.select("a").remove();
                    paragraph.select(".media-object-article-reader").remove();
                    paragraph.select("#chart_be3d84a89351d063731809fb").remove();
                    paragraph.select("div[data-layout]").remove();
                    paragraph.select("#cx-membership-tile").remove();


                    StringBuilder content = new StringBuilder();


                    Elements scripts = paragraph.select("script");


                    content.append(removeComments(paragraph.html()));

                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Economist")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraph = doc.select("div .ekpjo0f2");

//            paragraph.select(".css-1lm38nn").remove();
                    paragraph.select("style[data-emotion]").remove();
                    paragraph.select("i").remove();

                    StringBuilder content = new StringBuilder();


                    Elements scripts = paragraph.select("script");


                    content.append(removeComments(paragraph.html()));
                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertNbaContent() {
        List<Article> articles = articleDao.getNbaArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("CBS Sports")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Element paragraph = doc.selectFirst(".Article-bodyContent");
                    StringBuilder content = new StringBuilder();
                    // Check if paragraph contains class "NewsFeed-title"
                    if (!paragraph.hasClass("NewsFeed-title")) {
                        // Get all <p> elements within the article body content
                        Elements paragraphs = paragraph.select("p");

                        // Iterate over each <p> element
                        for (Element p : paragraphs) {
                            // Check if paragraph contains any unwanted text
                            if (p.text().contains("Play Now") && p.text().contains("Football Pick'em") && p.text().contains("Play Now") && p.text().contains("College Football Pick'em") && p.text().contains("Your Daily NFL Fix") && p.text().contains("Daily Soccer Podcast") && p.text().contains("© 2004-2023 CBS Interactive. All Rights Reserved.") && p.text().contains("CBS Sports is a registered trademark of CBS Broadcasting Inc. Commissioner.com is a registered trademark of CBS Interactive Inc.")
                                    && p.text().contains("Our Latest NFL Stories") && p.text().contains("Thanks for signing up") && p.text().contains("Sorry") && p.text().contains("Crafted By The Best NFL Experts") && p.text().contains("View Profile") && p.text().contains("click here")) {
                                // Remove the paragraph from the parent element
                                p.remove();
                            }
                        }

                        // Select all <img> elements within the article body content
                        Elements images = paragraph.select("img[data-lazy]");

                        // Iterate over each image element
                        for (Element img : images) {
                            // Get the value of the data-lazy attribute
                            String lazySrc = img.attr("data-lazy");

                            // Set the value of the srcset attribute to the value of data-lazy
                            img.attr("srcset", lazySrc);

                            // Remove the data-lazy attribute
                            img.removeAttr("data-lazy");
                        }

                        Elements scripts = paragraph.select("script");

                        // Iterate over each div element
                        for (Element script : scripts) {
                            if (script.hasAttr("src") && script.attr("src").equals("https://platform.twitter.com/widgets.js")) {
                                // Add the Vue directive to the script
                                script.attr("is", "vue:script");
                                // Replace the script element with a div element
                                Element div = new Element(Tag.valueOf("div"), "");
                                // Copy attributes from the script to the div
                                for (Attribute attribute : script.attributes()) {
                                    div.attr(attribute.getKey(), attribute.getValue());
                                }
                                // Copy the content of the script to the div
                                div.text(script.data());
                                // Replace the script with the new div
                                script.replaceWith(div);

                            }
                        }

                    }

                    // Now you can access the modified HTML content
                    String modifiedHtml = paragraph.html();
                    content.append(modifiedHtml);

                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());

                }else if (article.getName().equals("NBCSports.com")) {
                        String articleUrl = article.getUrl();
                        Document doc = Jsoup.connect(articleUrl).get();
                        StringBuilder content = new StringBuilder();
                        Elements pElements = doc.select(".entry-content p");
                        Elements backupPElements = doc.select(".field p");
                        Set<String> tweetUrls = new HashSet<>();
                        for (Element pElement: pElements) {

                            if(!pElement.text().contains("https://www.facebook.com/"))
                            content.append("<p>" + pElement.text() + "</p>" + System.lineSeparator() + System.lineSeparator());



                            Elements aTags = doc.select("a[href*=twitter.com]");
                            for (Element aTag : aTags) {
                                String tweetUrl = aTag.attr("href").split("\\?")[0];
                                String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                                if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                    // Construct the API URL with the tweet URL as a query parameter
                                    String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                    URL url = new URL(apiUrl);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");
                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder response = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();
                                    JSONObject jsonResponse = new JSONObject(response.toString());
                                    String html = jsonResponse.getString("html");
                                    String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                    String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                    if (blockquoteHtml != null) {
                                        content.append(removingScriptTags);
                                        tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                    }
                                }
                            }
                        }
                    for (Element pElement: backupPElements) {

                        content.append("<p>" + pElement.text() + "</p>" + System.lineSeparator() + System.lineSeparator());

                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                        String contentString = content.toString();


                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("NBCSports.com")) {
                        String articleUrl = article.getUrl();
                        Document doc = Jsoup.connect(articleUrl).get();
                        Elements pElements = doc.select("p");
                        StringBuilder content = new StringBuilder();
                        for (int i = 0; i < pElements.size(); i++) {
                            Element paragraph = pElements.get(i);
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());


                            Elements aTags = paragraph.select("a[href*=twitter.com]");
                            for (Element aTag : aTags) {
                                String tweetUrl = aTag.attr("href").split("\\?")[0];
                                String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                                if (tweetUrl.matches(tweetRegex)) {
                                    // Construct the API URL with the tweet URL as a query parameter
                                    String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                    URL url = new URL(apiUrl);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");


                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder response = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();


                                    JSONObject jsonResponse = new JSONObject(response.toString());
                                    String html = jsonResponse.getString("html");

                                    String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                    String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                    if (blockquoteHtml != null) {
                                        content.append(removingScriptTags);
                                    }

                                }
                            }
                        }
                    String contentString = content.toString();



                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

    @Override
    public void insertNflContent() {
        List<Article> articles = articleDao.getNflArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("CBS Sports")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Element paragraph = doc.selectFirst(".Article-bodyContent");
                    StringBuilder content = new StringBuilder();
                    // Check if paragraph contains class "NewsFeed-title"
                    if (!paragraph.hasClass("NewsFeed-title")) {
                        // Get all <p> elements within the article body content
                        Elements paragraphs = paragraph.select("p");

                        // Iterate over each <p> element
                        for (Element p : paragraphs) {
                            // Check if paragraph contains any unwanted text
                            if (p.text().contains("Play Now") && p.text().contains("Football Pick'em") && p.text().contains("Play Now") && p.text().contains("College Football Pick'em") && p.text().contains("Your Daily NFL Fix") && p.text().contains("Daily Soccer Podcast") && p.text().contains("© 2004-2023 CBS Interactive. All Rights Reserved.") && p.text().contains("CBS Sports is a registered trademark of CBS Broadcasting Inc. Commissioner.com is a registered trademark of CBS Interactive Inc.")
                                    && p.text().contains("Our Latest NFL Stories") && p.text().contains("Thanks for signing up") && p.text().contains("Sorry") && p.text().contains("Crafted By The Best NFL Experts") && p.text().contains("View Profile") && p.text().contains("click here")) {
                                // Remove the paragraph from the parent element
                                p.remove();
                            }
                        }

                        // Select all <img> elements within the article body content
                        Elements images = paragraph.select("img[data-lazy]");

                        // Iterate over each image element
                        for (Element img : images) {
                            // Get the value of the data-lazy attribute
                            String lazySrc = img.attr("data-lazy");

                            // Set the value of the srcset attribute to the value of data-lazy
                            img.attr("srcset", lazySrc);

                            // Remove the data-lazy attribute
                            img.removeAttr("data-lazy");
                        }

                        Elements scripts = paragraph.select("script");

                        // Iterate over each div element
                        for (Element script : scripts) {
                            if (script.hasAttr("src") && script.attr("src").equals("https://platform.twitter.com/widgets.js")) {
                                // Add the Vue directive to the script
                                script.attr("is", "vue:script");
                                // Replace the script element with a div element
                                Element div = new Element(Tag.valueOf("div"), "");
                                // Copy attributes from the script to the div
                                for (Attribute attribute : script.attributes()) {
                                    div.attr(attribute.getKey(), attribute.getValue());
                                }
                                // Copy the content of the script to the div
                                div.text(script.data());
                                // Replace the script with the new div
                                script.replaceWith(div);

                            }
                        }

                    }

                    // Now you can access the modified HTML content
                    String modifiedHtml = paragraph.html();
                    content.append(modifiedHtml);

                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());

                } else if (article.getName().equals("NFL News")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".nfl-c-article__container p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Follow Ian Rapoport on Twitter.")) {
                            content.append(paragraph.text() + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertNhlContent() {
        List<Article> articles = articleDao.getNhlArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("CBS Sports")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Element paragraph = doc.selectFirst(".Article-bodyContent");
                    StringBuilder content = new StringBuilder();
                    // Check if paragraph contains class "NewsFeed-title"
                    if (!paragraph.hasClass("NewsFeed-title")) {
                        // Get all <p> elements within the article body content
                        Elements paragraphs = paragraph.select("p");

                        // Iterate over each <p> element
                        for (Element p : paragraphs) {
                            // Check if paragraph contains any unwanted text
                            if (p.text().contains("Play Now") && p.text().contains("Football Pick'em") && p.text().contains("Play Now") && p.text().contains("College Football Pick'em") && p.text().contains("Your Daily NFL Fix") && p.text().contains("Daily Soccer Podcast") && p.text().contains("© 2004-2023 CBS Interactive. All Rights Reserved.") && p.text().contains("CBS Sports is a registered trademark of CBS Broadcasting Inc. Commissioner.com is a registered trademark of CBS Interactive Inc.")
                                    && p.text().contains("Our Latest NFL Stories") && p.text().contains("Thanks for signing up") && p.text().contains("Sorry") && p.text().contains("Crafted By The Best NFL Experts") && p.text().contains("View Profile") && p.text().contains("click here")) {
                                // Remove the paragraph from the parent element
                                p.remove();
                            }
                        }

                        // Select all <img> elements within the article body content
                        Elements images = paragraph.select("img[data-lazy]");

                        // Iterate over each image element
                        for (Element img : images) {
                            // Get the value of the data-lazy attribute
                            String lazySrc = img.attr("data-lazy");

                            // Set the value of the srcset attribute to the value of data-lazy
                            img.attr("srcset", lazySrc);

                            // Remove the data-lazy attribute
                            img.removeAttr("data-lazy");
                        }

                        Elements scripts = paragraph.select("script");

                        // Iterate over each div element
                        for (Element script : scripts) {
                            if (script.hasAttr("src") && script.attr("src").equals("https://platform.twitter.com/widgets.js")) {
                                // Add the Vue directive to the script
                                script.attr("is", "vue:script");
                                // Replace the script element with a div element
                                Element div = new Element(Tag.valueOf("div"), "");
                                // Copy attributes from the script to the div
                                for (Attribute attribute : script.attributes()) {
                                    div.attr(attribute.getKey(), attribute.getValue());
                                }
                                // Copy the content of the script to the div
                                div.text(script.data());
                                // Replace the script with the new div
                                script.replaceWith(div);

                            }
                        }

                    }

                    // Now you can access the modified HTML content
                    String modifiedHtml = paragraph.html();
                    content.append(modifiedHtml);

                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());

                } else if (article.getName().equals("NHL News")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".article-item__body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append(paragraph.text() + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertMmaContent() {
        List<Article> articles = articleDao.getMmaArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("MMA Fighting")) {
                    String articleUrl = article.getUrl();
                    Set<String> tweetUrls = new HashSet<>();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".c-entry-content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>"+paragraph.text()+"</p>" + System.lineSeparator() + System.lineSeparator());

                        Elements youtubeVids = doc.select("#player div");
                        for (Element youtubeVid : youtubeVids){
                            content.append(youtubeVid.html());
                        }

                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();

                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("ESPN")) {
                    String articleUrl = article.getUrl();
                    Set<String> tweetUrls = new HashSet<>();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".article-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());

                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }

                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
//                } else if (article.getName().equals("ESPN")) {
//                    String url = article.getUrl();
//                    Document doc = Jsoup.connect(url).get();
//                    Elements pElements = doc.select(".article-body p");
//                    StringBuilder content = new StringBuilder();
//                    for (Element paragraph : pElements) {
//                        content.append(paragraph.text() + System.lineSeparator() + System.lineSeparator());
//                    }
//                    String contentString = content.toString();
//                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
//                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertMovieContent() {
        List<Article> articles = articleDao.getMovieArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Variety")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".vy-cx-page-content p");
                    Elements backupPElements = doc.select(".a-content p");
                    Elements imageElements = doc.select(".post-content-image div");
                    Elements h4elements = doc.select("h4");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(paragraph.html().contains("<strong>")){
                            content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        if(!paragraph.html().contains("<strong")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for(Element h4 : h4elements){
                            if(h4.previousElementSibling() == paragraph){
                                content.append("h4" + h4.text() + "</h4>");
                            }
                        }
                        for(Element img : imageElements){
                            if(img.previousElementSibling() == paragraph){
                                content.append(img.html());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");


                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();


                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");

                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                }

                            }
                        }
                    }
                    for (Element paragraph : backupPElements) {
                        if(paragraph.html().contains("<strong>")){
                            content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        if(!paragraph.html().contains("<strong")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for(Element h4 : h4elements){
                            if(h4.previousElementSibling() == paragraph){
                                content.append("h4" + h4.text() + "</h4>");
                            }
                        }
                        for(Element img : imageElements){
                            if(img.previousElementSibling() == paragraph){
                                content.append(img.html());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");


                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();


                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");

                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                }

                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Hollywood Reporter")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".a-content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Click here to subscribe.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Eonline.com")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".article-detail__text-only p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Entertainment Weekly")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".articleContainer__content p");
                    Elements backupPElements = doc.select(".longformContent p");
                    Elements backupH3Elements = doc.select("h3");
                    Elements h3Elements = doc.select(".articleContainer__content h3");
                    Elements images = doc.select(".lazy-image div");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                        for (Element image : images) {
                            if (image.previousElementSibling() == paragraph) {
                                content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element h3 : h3Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                    }
                    for (Element paragraph : backupPElements) {
                        content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                        for (Element image : images) {
                                content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                        }
                        for (Element h3 : backupH3Elements) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertTvContent() {
        List<Article> articles = articleDao.getTvArticles();


        for (Article article : articles) {
            try {
                    if (article.getName().equals("Variety")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".vy-cx-page-content p");
                        Elements backupPElements = doc.select(".a-content p");
                        Elements imageElements = doc.select(".post-content-image div");
                        Elements h4elements = doc.select("h4");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            if(paragraph.html().contains("<strong>")){
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            if(!paragraph.html().contains("<strong")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            for(Element h4 : h4elements){
                                if(h4.previousElementSibling() == paragraph){
                                    content.append("h4" + h4.text() + "</h4>");
                                }
                            }
                            for(Element img : imageElements){
                                if(img.previousElementSibling() == paragraph){
                                    content.append(img.html());
                                }
                            }
                        }
                        for (Element paragraph : backupPElements) {
                            if(paragraph.html().contains("<strong>")){
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            if(!paragraph.html().contains("<strong")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            for(Element h4 : h4elements){
                                if(h4.previousElementSibling() == paragraph){
                                    content.append("h4" + h4.text() + "</h4>");
                                }
                            }
                            for(Element img : imageElements){
                                if(img.previousElementSibling() == paragraph){
                                    content.append(img.html());
                                }
                            }
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("Hollywood Reporter")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".a-content p");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            if (!paragraph.text().contains("Click here to subscribe.")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("Eonline.com")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".article-detail__text-only p");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    } else if (article.getName().equals("Entertainment Weekly")) {
                        String url = article.getUrl();
                        Document doc = Jsoup.connect(url).get();
                        Elements pElements = doc.select(".articleContainer__content p");
                        Elements backupPElements = doc.select(".longformContent p");
                        Elements backupH3Elements = doc.select("h3");
                        Elements h3Elements = doc.select(".articleContainer__content h3");
                        Elements images = doc.select(".lazy-image div");
                        StringBuilder content = new StringBuilder();
                        for (Element paragraph : pElements) {
                            content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                            for (Element image : images) {
                                if (image.previousElementSibling() == paragraph) {
                                    content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                                }
                            }
                            for (Element h3 : h3Elements) {
                                if (h3.previousElementSibling() == paragraph) {
                                    content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                                }
                            }
                        }
                        for (Element paragraph : backupPElements) {
                            content.append(paragraph.html() + System.lineSeparator() + System.lineSeparator());
                            for (Element image : images) {
                                content.append(image.html() + System.lineSeparator() + System.lineSeparator());
                            }
                            for (Element h3 : backupH3Elements) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        String contentString = content.toString();
                        String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                        jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertMusicContent() {
        List<Article> articles = articleDao.getMusicArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Variety")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".vy-cx-page-content p");
                    Elements backupPElements = doc.select(".a-content p");
                    Elements imageElements = doc.select(".post-content-image div");
                    Elements h4elements = doc.select("h4");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Listen to “")){
                            if (paragraph.html().contains("<strong>")) {
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                        if (!paragraph.html().contains("<strong")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for (Element h4 : h4elements) {
                            if (h4.previousElementSibling() == paragraph) {
                                content.append("h4" + h4.text() + "</h4>");
                            }
                        }
                        for (Element img : imageElements) {
                            if (img.previousElementSibling() == paragraph) {
                                content.append(img.html());
                            }
                        }
                    }
                    }
                    for (Element paragraph : backupPElements) {
                        if (!paragraph.text().contains("Listen to “")) {
                            if (paragraph.html().contains("<strong>")) {
                                content.append("<p>" + "<strong>" + paragraph.text() + "</strong>" + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            if (!paragraph.html().contains("<strong")) {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                            for (Element h4 : h4elements) {
                                if (h4.previousElementSibling() == paragraph) {
                                    content.append("h4" + h4.text() + "</h4>");
                                }
                            }
                            for (Element img : imageElements) {
                                if (img.previousElementSibling() == paragraph) {
                                    content.append(img.html());
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Hollywood Reporter")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".a-content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("Click here to subscribe.")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Hip-Hop Wired")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".post-content p");
                    Elements h3Elements = doc.select("h3");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if (!paragraph.text().contains("RELATED TAGS")) {
                            if(paragraph.html().contains("<em>")){
                                content.append("<p>" + "<em>" + paragraph.text() + "</p>" + "</em>" + System.lineSeparator() + System.lineSeparator());
                            } else {
                                content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element h3 : h3Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");


                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");


                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();


                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");

                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                }

                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertCenterContent() {
        List<Article> articles = articleDao.getCenterArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Zerohedge.com")) {
                    String articleUrl = article.getUrl();
                    Set<String> tweetUrls = new HashSet<>();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select("p");
                    Elements imgElements = doc.select("img");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("figure")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Wall Street Journal")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements paragraphs = doc.select("p");
                    StringBuilder content = new StringBuilder();
                    content.append("You need to Subscribe to the WSJ in order to view this content. If you have a subscription feel free to click the link above to read the article, and make sure to come right back and drop a comment down below!" + System.lineSeparator() + System.lineSeparator());
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Axios")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".DraftjsBlocks_draftjs__jDHy3 p");
                    Elements ulAfterPElements = doc.select("ul");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element ulAfterPElement : ulAfterPElements){
                            Element previousElement = ulAfterPElement.previousElementSibling();
                            if(previousElement == paragraph){
                                content.append(ulAfterPElement.html());
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Hill")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".article__text p");
                    Elements backUpElements = doc.select("ca-article__content p");
                    Elements h2ForbackUpElements = doc.select("ca-article__content h2");
                    Elements lineSeperators = doc.select("ca-article__content hr");
                    Elements ulForLineSeperators = doc.select("ul");
                    Elements relatedStories = doc.select("aside");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }

                    for (Element h2 : h2ForbackUpElements) {
                        for(Element aside : relatedStories)
                        if (h2.previousElementSibling() == aside){
                            content.append("<h2>" + h2.text() + "</h2>" + System.lineSeparator() + System.lineSeparator());
                        for (Element lineSeperator : lineSeperators) {
                            if (lineSeperator.previousElementSibling() == h2) {
                                content.append(lineSeperator.html());
                                for (Element ul : ulForLineSeperators) {
                                    if (ul.previousElementSibling() == lineSeperator) {
                                        content.append(ul);
                                    }
                                }
                            }
                        }
                        for (Element paragraph : backUpElements) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    }

                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertLeftContent() {
        List<Article> articles = articleDao.getLeftWingArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Politico")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".story-text p");
                    Elements photosForStory = doc.select(".story-photo figure");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("Link Copied") && !paragraph.text().contains("/AP Photo")){
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                        for(Element photo:photosForStory){

                            content.append(photo.html());

                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Slate Magazine")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".article__content p");
                    Elements h3Elements = doc.select("h3");
                    Set<String> tweetUrls = new HashSet<>();
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element h3 : h3Elements) {
                            if (h3.previousElementSibling() == paragraph) {
                                content.append("<h3>" + h3.text() + "</h3>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Associated Press")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".Article p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Time")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select("#article-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {

                        if(!paragraph.text().contains("@time") && !paragraph.text().contains("Follow this link to learn more") && !paragraph.text().contains("Read More")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The New Yorker")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select("#article-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void insertRightContent() {
        List<Article> articles = articleDao.getRightWingArticles();


        for (Article article : articles) {
            try {
                if (article.getName().equals("Thegatewaypundit.com")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".entry-content p");
                    Elements imgElements = doc.select("img");
                    Set<String> tweetUrls = new HashSet<>();
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("p")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Zerohedge.com")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".NodeContent_body__6iJOI p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("Assistance and Requests:") && !paragraph.text().contains("@zerohedge.com") && !paragraph.text().contains("Cookie Policy")){
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The Federalist")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Set<String> tweetUrls = new HashSet<>();
                    Elements pElements = doc.select(".article-content p");
                    Elements ptagBackup = doc.select("p");
                    Elements imgElements = doc.select("img");
                    Elements youtubeVids = doc.select("iframe");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : ptagBackup) {
                        if(!paragraph.text().contains("All Rights Reserved") && !paragraph.text().contains("Join Now") && !paragraph.text().contains("Free Trial") && !paragraph.text().contains("[READ:") && !paragraph.text().contains("FREE TRIAL"))
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("figure")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element youtube : youtubeVids){
                            content.append(youtube.html());
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("All Rights Reserved") && !paragraph.text().contains("Join Now") && !paragraph.text().contains("Free Trial") && !paragraph.text().contains("[READ:") && !paragraph.text().contains("FREE TRIAL"))
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element parentElement = img.parent();
                            if (parentElement.tagName().equals("figure")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                        for (Element youtube : youtubeVids){
                            content.append(youtube.html());
                        }
                        Elements aTags = doc.select("a[href*=twitter.com]");
                        for (Element aTag : aTags) {
                            String tweetUrl = aTag.attr("href").split("\\?")[0];
                            String tweetRegex = "https://twitter.com/(?!(?:i\\/|.*timelines\\/))\\w+/status/\\d+";
                            if (tweetUrl.matches(tweetRegex) && !tweetUrls.contains(tweetUrl)) {
                                // Construct the API URL with the tweet URL as a query parameter
                                String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");
                                URL url = new URL(apiUrl);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder response = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }
                                in.close();
                                JSONObject jsonResponse = new JSONObject(response.toString());
                                String html = jsonResponse.getString("html");
                                String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");
                                String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");
                                if (blockquoteHtml != null) {
                                    content.append(removingScriptTags);
                                    tweetUrls.add(tweetUrl); // Add the tweet URL to the set
                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("New York Post")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".single__content p");
                    Elements imgElements = doc.select("img");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element img : imgElements) {
                            Element previouselement = img.previousElementSibling();
                            if (previouselement != null && previouselement.tagName() != null && previouselement.tagName().equals("button")) {
                                content.append(img.html() + System.lineSeparator() + System.lineSeparator());
                            }
                        }

                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("The American Conservative")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".c-blog-post__content p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("Reason")) {
                    String url = article.getUrl();
                    Document doc = Jsoup.connect(url).get();
                    Elements pElements = doc.select(".entry-content p");
                    Elements h2Elements = doc.select("h2");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                        for (Element h2 : h2Elements) {
                            if (h2.previousElementSibling() == paragraph) {
                                content.append("<h2>" + h2.text() + "</h2>" + System.lineSeparator() + System.lineSeparator());
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, url, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                } else if (article.getName().equals("TheBlaze")) {
                    String articleUrl = article.getUrl();
                    Document doc = Jsoup.connect(articleUrl).get();
                    Elements pElements = doc.select(".post-body p");
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : pElements) {
                        if(!paragraph.text().contains("Sign up")) {
                            content.append("<p>" + paragraph.text() + "</p>" + System.lineSeparator() + System.lineSeparator());
                            Elements aTags = paragraph.select("a[href*=twitter.com]");
                            for (Element aTag : aTags) {
                                String tweetUrl = aTag.attr("href").split("\\?")[0];
                                String tweetRegex = "https://twitter.com/[a-zA-Z0-9_]+/status/\\d+";
                                if (tweetUrl.matches(tweetRegex)) {
                                    // Construct the API URL with the tweet URL as a query parameter
                                    String apiUrl = "https://publish.twitter.com/oembed?url=" + URLEncoder.encode(tweetUrl, "UTF-8");

                                    URL url = new URL(apiUrl);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");

                                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLine;
                                    StringBuilder response = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();

                                    JSONObject jsonResponse = new JSONObject(response.toString());
                                    String html = jsonResponse.getString("html");

                                    String blockquoteHtml = html.replaceAll("(?s)<blockquote[^>]*>((?:(?!</?script)[\\s\\S])*?)</blockquote>", "<blockquote class=\"twitter-tweet\">$1</blockquote>");


                                    String removingScriptTags = blockquoteHtml.replaceAll("<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", "");


                                    if (blockquoteHtml != null) {
                                        content.append(removingScriptTags);
                                    }

                                }
                            }
                        }
                    }
                    String contentString = content.toString();
                    String sql = "INSERT INTO article_content (article_id, title, text, url, url_to_image, category, category_specified) VALUES (?,?,?,?,?,?,?) ON CONFLICT (article_id) DO NOTHING;";
                    jdbcTemplate.update(sql, article.getId(), article.getTitle(), contentString, articleUrl, article.getUrlToImage(), article.getCategory(), article.getCategorySpecified());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    private static String removeComments(String html) {
        // Use regular expression to remove HTML comments
        return html.replaceAll("<!--(.*?)-->", "");
    }
}
