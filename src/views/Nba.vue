<template>
  <div class="container">
      <div>
          <navBar v-bind:class="{hidden: !isDesktop}"/>
          <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
      </div>
      <div>
        <!-- conditional rendering in mobile view -->
          <h1 class="heading-holder" v-if="newsOrTrendingClicked">NBA Section <i class="fa-sharp fa-solid fa-basketball"></i></h1>
          <h1 class="heading-holder" v-if="!newsOrTrendingClicked">Trending <i class="fa fa-fire"></i> </h1>
          <div class="main-area">
            <!-- Trending Sidebar -->
            <div id = "trending">
              <div> 
                <input class="search-bar" type="text" placeholder="Search Through News Titles..." v-model="term" v-on:keyup.enter="searchArticles(term)"> 
                <span> <i class="fa-solid fa-magnifying-glass fa-lg" ></i> </span>                
              </div>
              <h2>TRENDING ARTICLES</h2>
              <!-- for loop to iterate through the trending articles array -->
            <div class = "trending-articles"  v-for="articles in trendingArticles" v-bind:key="articles.articleId">
            <div class="trending-information-section">
              <div class="trending-title-container">
                <a  v-on:click ="pushToArticle(articles.categorySpecified, articles.title, articles.articleId)">
                  <h4 class = "trending-title" v-on:click="postClicked=article.id; logArticleClick()">{{articles.title}}</h4>
                </a>
          </div>
            </div>
            <div class="trending-data">
            <h2 class = "trending-category">{{ articles.category }}</h2>
            <!-- only going to show likes for trending articles -->
              <h2 class = "trending-like-count">Likes<button v-on:click="postLiked = articles.articleId; handleLikeClick(articles.articleId)"><i class="fa fa-fire" v-bind:class="{'i-changed': articleIsLiked(parseInt(articles.articleId))}"></i></button>: {{ articles.likeCount }}</h2>
            </div>
            <h2 class = "trending-published">On: {{ articles.publishedAt }}</h2>
          </div>
       
            </div>
            <div class="nba-scores" v-show="!toggleContentBasedCollaborativeBased || isDesktop"> 
              <NbaScores></NbaScores>
            <!-- main news grid, v-if relates to the buttons press in mobile view -->
            <div class = "news-grid" v-show="!toggleContentBasedCollaborativeBased || isDesktop">
          
              <!-- all articles in currect subject data -->
              <div class = "article" v-for="article in displayArticles" v-bind:key="article.id">
                <div>
                  <img class="article-image" v-lazy=article.urlToImage alt="article-image" preload="auto">
                  <h3 class = "category">published at: {{ article.publishedAt }}</h3>
                </div>
                <a  v-on:click ="pushToArticle(article.categorySpecified, article.title, article.id)">
                  <h2 class="title" v-on:click="postClicked=article.id; logArticleClick()">{{article.title}}</h2>
                </a>
                <!-- likes dislikes and save for later buttons -->
                <div class = "buttons">
                  <button v-on:click="postLiked = article.id; handleLikeClick(article.id)"><i class="fa fa-thumbs-up fa-regular" v-bind:class="{'i-changed': articleIsLiked(article.id)}"></i></button>
                  <button v-on:click="postDisliked = article.id; handleDislikeClick(article.id)"><i class="fa-regular fa-thumbs-down" v-bind:class="{'i-changed': articleIsDisliked(article.id)}"></i></button>
                  <button v-on:click="saved = article.id; handleSaveForLaterClick(article.id)"><i class="fa fa-regular fa-bookmark" v-bind:class="{'i-changed': articleIsSavedForLater(article.id)}"></i></button>
                </div>
              </div>
            </div>
            
          </div>
          <i class="fa-solid fa-arrow-right fa-lg toggle-main-content-right" v-if="!toggleContentBasedCollaborativeBased" v-on:click="toggleContentBasedCollaborativeBased = true"></i>
            <i class="fa-solid fa-arrow-left fa-lg toggle-main-content-left" v-if="toggleContentBasedCollaborativeBased" v-on:click="toggleContentBasedCollaborativeBased = false"></i>
            <FwlsRecommendationsSideBar v-show="toggleContentBasedCollaborativeBased && !isDesktop"></FwlsRecommendationsSideBar>
            <FwlsRecommendationsSideBar v-if="isDesktop"></FwlsRecommendationsSideBar>
          </div>
      </div>
      <!-- bottom nav bar in mobile mode -->
      <BottomNavigation></BottomNavigation>
  </div>  
  
  </template>
  
  <script>
  
  import navBar from '../components/Navbar.vue';
  import MobileNavbar from '../components/MobileNavbar.vue'
  import ArticleService from '../services/ArticleService';
  import ArticleDataService from '../services/ArticleDataService';
  import LoggerService from '@/services/LoggerService';
  import FwlsRecommendationsSideBar from '@/components/FwlsRecommendationsSideBar.vue';
  import NbaScores from '@/components/NbaScores.vue';
  import BottomNavigation from '../components/BottomNavigation.vue';

  export default {
    name: "nbaPage",
    components: {
      navBar,
      MobileNavbar,
      FwlsRecommendationsSideBar,
      NbaScores,
      BottomNavigation
    },
    data() {
      return {
         // //crsf token
        // crsf: '',
        toggleContentBasedCollaborativeBased: false,
        //navbar toggle data property
        isDesktop: false,
        //articles array
        articles: [
          {
        "id": 135000,
        "name": "CBS Sports",
        "author": "CBS Sports Staff",
        "title": "Celtics vs. Bucks odds, line, score prediction, time: 2024 NBA picks, April 9 best bets from proven model",
        "description": "SportsLine's model just revealed its NBA picks for Boston Celtics vs. Milwaukee Bucks on Tuesday",
        "url": "https://www.cbssports.com/nba/news/celtics-vs-bucks-odds-line-score-prediction-time-2024-nba-picks-april-9-best-bets-from-proven-model/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/02/15/c1cdd13f-fea7-445f-b1f4-c7ad9fbc30b1/thumbnail/1200x675/d61e0ddea2ce92cb92665d673a0358d7/damian-lillard-usatsi-cbs.jpg",
        "publishedAt": "2024-04-09",
        "content": "The Boston Celtics (62-16) go on the road to play the Milwaukee Bucks (47-31) in an Eastern Conference showdown on Tuesday. With the playoffs quickly approaching, the Celtics have found a nice groove… [+3562 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135001,
        "name": "CBS Sports",
        "author": "CBS Sports Staff",
        "title": "Warriors vs. Lakers odds, line, score prediction, time: 2024 NBA picks, April 9 best bets from proven model",
        "description": "SportsLine's model just revealed its NBA picks for Los Angeles Lakers vs. Golden State Warriors on Tuesday",
        "url": "https://www.cbssports.com/nba/news/warriors-vs-lakers-odds-line-score-prediction-time-2024-nba-picks-april-9-best-bets-from-proven-model/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/02/10/2fd8ecf9-ae4e-496d-9f8c-b3382576eb80/thumbnail/1200x675/471d43dd1d74490557662d557e11b1df/stephen-curry-warriors-usatsi.jpg",
        "publishedAt": "2024-04-09",
        "content": "The Los Angeles Lakers (45-34) and the Golden State Warriors (43-35) square off in a Western Conference battle on Tuesday evening that could be a preview of a play-in matchup. The Warriors have won s… [+3321 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135002,
        "name": "CBS Sports",
        "author": "Colin Ward-Henninger",
        "title": "NBA Power Rankings: Joel Embiid's return fuels huge 76ers jump, Bucks searching for answers in final week",
        "description": "The NBA's power structure is still in flux as we enter the final week of the regular season",
        "url": "https://www.cbssports.com/nba/news/nba-power-rankings-joel-embiids-return-fuels-huge-76ers-jump-bucks-searching-for-answers-in-final-week/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/dcb05118-230d-4348-a7a6-27d88871d362/thumbnail/1200x675/1bde04c5029521ccbd0b84b9307b3049/nbapowerrankings0409.png",
        "publishedAt": "2024-04-09",
        "content": "With less than a week left in the NBA regular season, there are still spots up for grabs everywhere you look in the standings. We know who the 20 postseason teams will be, but other than that very li… [+15758 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135003,
        "name": "CBS Sports",
        "author": "Zachary Pereles",
        "title": "UConn stomps Purdue, repeats as national champions; Storylines, sleepers for 2024 Masters",
        "description": "The Huskies pulled away late thanks to a sterling team effort",
        "url": "https://www.cbssports.com/college-basketball/news/uconn-stomps-purdue-repeats-as-national-champions-storylines-sleepers-for-2024-masters/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/4e6413ec-d170-4b36-b9a3-2ab69e9d3a05/thumbnail/1200x675/5c34f352e9bea58776f65eb56ff16032/uconn-wins-getty.jpg",
        "publishedAt": "2024-04-09",
        "content": "This is an article version of the CBS Sports HQ AM Newsletter, the ultimate guide to every day in sports. You can sign up to get it in your inbox every weekday morning here.\r\nGood morning to everyone… [+9629 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135004,
        "name": "CBS Sports",
        "author": "Dennis Dodd",
        "title": "For Zach Edey, like Caitlin Clark, a somber end to an incredible college career",
        "description": "Edey and Clark were not able to carry their teams all the way to the title, but don't sell them short",
        "url": "https://www.cbssports.com/college-basketball/news/for-zach-edey-like-caitlin-clark-a-somber-end-to-an-incredible-college-career/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/00edb918-e14e-41d8-a627-a8ac7a0738bb/thumbnail/1200x675/3832332e91093cf528dfde7b91ffc222/gettyimages-2147667550.jpg",
        "publishedAt": "2024-04-09",
        "content": "GLENDALE, Ariz. -- Zach Edey went quietly. \r\nNo, not like that. The man had 37 points Monday night against UConn, the most in a national championship game since 1978. But Purdue's 7-foot-4 center lef… [+6458 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135005,
        "name": "CBS Sports",
        "author": "Zachary Pereles",
        "title": "UConn wins 2024 national championship: Ranking all 12 NCAA Tournament wins in back-to-back title run",
        "description": "The Huskies' historic run includes one of the highest average scoring margins ever",
        "url": "https://www.cbssports.com/college-basketball/news/uconn-wins-2024-national-championship-ranking-all-12-ncaa-tournament-wins-in-back-to-back-title-run/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/1f5aad56-3416-4fdd-a243-0d2ac9c02b8a/thumbnail/1200x675/82c4b0f66191cbe0df531d51bf7f15e6/uconn-celebrates-1.jpg",
        "publishedAt": "2024-04-09",
        "content": "With its 75-60 win Monday, UConn became the first repeat champion since 2006-07 Florida. But the Huskies didn't just repeat. They dominated. Steamrolled. Crushed. Whatever verb you want, UConn did it… [+6910 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135006,
        "name": "CBS Sports",
        "author": "David Cobb",
        "title": "UConn coach Dan Hurley joins brother Bobby, former Duke star, as repeat national champion",
        "description": "Monday's win gave UConn coach Dan Hurley something new in common with his brother Bobby",
        "url": "https://www.cbssports.com/college-basketball/news/uconn-coach-dan-hurley-joins-brother-bobby-former-duke-star-as-repeat-national-champion/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/47d1fde2-6452-4717-9516-751177ad5bd8/thumbnail/1200x675/64e3974f0aa06407129eeb933faf319c/gettyimages-2147670844-1.jpg",
        "publishedAt": "2024-04-09",
        "content": "UConn's 75-60 win over Purdue in the national championship game on Monday gave Huskies coach Dan Hurley something new in common with his big brother. Bobby Hurley was the starting point guard for Duk… [+4062 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135007,
        "name": "CBS Sports",
        "author": "Cameron Salerno",
        "title": "Kentucky coaching candidates: Dan Hurley, Nate Oats both off list that includes Billy Donovan, Rick Pitino",
        "description": "The Wildcats will be able to pick from a quality pool of candidates to replace Calipari",
        "url": "https://www.cbssports.com/college-basketball/news/kentucky-coaching-candidates-dan-hurley-nate-oats-both-off-list-that-includes-billy-donovan-rick-pitino/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/e2709636-37fa-4b41-b57c-dd1f7ce51ea4/thumbnail/1200x675/0a400bd9e4b80e6227463c00f6781493/usatsi-22835835-1.jpg",
        "publishedAt": "2024-04-09",
        "content": "One of the top jobs in college basketball opened unexpectedly on Sunday with Kentucky coach John Calipari expected to take the vacant job at Arkansas after 15 seasons with the Wildcats. Kentucky will… [+5436 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135008,
        "name": "CBS Sports",
        "author": "Cameron Salerno",
        "title": "UConn repeats as NCAA Tournament champions: Comparing Huskies to other recent back-to-back title winners",
        "description": "UConn is the first team to repeat as national champion since Florida won titles in 2006-07",
        "url": "https://www.cbssports.com/college-basketball/news/uconn-repeats-as-ncaa-tournament-champions-comparing-huskies-to-other-recent-back-to-back-title-winners/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/c8be3238-6879-44e0-afd5-1c80f2a331c6/thumbnail/1200x675/55123681528813d3839062c9af7bb010/uconn-natty-1.jpg",
        "publishedAt": "2024-04-09",
        "content": "UConn completed its historic NCAA Tournament run with a 75-60 win over No. 1 seed Purdue in the national championship game on Monday in Glendale, Arizona. The Huskies became the first No. 1 overall s… [+13183 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135009,
        "name": "CBS Sports",
        "author": "Kyle Boone",
        "title": "2024 NBA Mock Draft: NCAA champion UConn puts three into first round, joined by Zach Edey",
        "description": "Plenty of college players showed their NBA potential with their performance in the NCAA Tournament",
        "url": "https://www.cbssports.com/nba/news/2024-nba-mock-draft-ncaa-champion-uconn-puts-three-into-first-round-joined-by-zach-edey/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/05/9e6ce440-3c69-44ed-854d-e768f4736f28/thumbnail/1200x675/d149eedc1774366825da16aff50a7b18/gettyimages-2139105231-1-1.jpg",
        "publishedAt": "2024-04-09",
        "content": "Having the most NBA talent on a college basketball team doesn't always equate to NCAA Tournament success -- if that were the case, Duke and Kentucky would be out of banner space in the rafters by now… [+1250 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135010,
        "name": "CBS Sports",
        "author": "David Cobb",
        "title": "UConn vs. Purdue live stream, watch online, channel, prediction, pick, spread, national championship game odds",
        "description": "The Huskies and Boilermakers are squaring off for the NCAA Tournament championship",
        "url": "https://www.cbssports.com/college-basketball/news/uconn-vs-purdue-live-stream-watch-online-channel-prediction-pick-spread-national-championship-game-odds/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/02/16/04163d67-04b4-4c1e-b9dc-f28882028e9f/thumbnail/1200x675/14633a974d9d64f78011dfc5d5460ef2/uconn-do-it-1.jpg",
        "publishedAt": "2024-04-09",
        "content": "After spending the entire 2023-24 season at or at least near the top of college basketball, Purdue and UConn are squaring off Monday night for the national championship. The matchup features the reig… [+5013 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135011,
        "name": "CBS Sports",
        "author": "Cameron Salerno",
        "title": "Kentucky coaching candidates: Nate Oats off list led by Billy Donovan, Rick Pitino, Dan Hurley, Scott Drew",
        "description": "The Wildcats will be able to pick from a quality pool of candidates to replace Calipari",
        "url": "https://www.cbssports.com/college-basketball/news/kentucky-coaching-candidates-nate-oats-off-list-led-by-billy-donovan-rick-pitino-dan-hurley-scott-drew/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/09/e2709636-37fa-4b41-b57c-dd1f7ce51ea4/thumbnail/1200x675/0a400bd9e4b80e6227463c00f6781493/usatsi-22835835-1.jpg",
        "publishedAt": "2024-04-09",
        "content": "One of the top jobs in college basketball opened unexpectedly on Sunday with Kentucky coach John Calipari expected to take the vacant job at Arkansas after 15 seasons with the Wildcats. Kentucky will… [+5569 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135012,
        "name": "CBS Sports",
        "author": "Cameron Salerno",
        "title": "John Calipari closing in on agreement with Arkansas: Kentucky coach taking over Razorbacks is 'imminent'",
        "description": "Calipari and Arkansas are expected to officially agree to terms in a volcanic shakeup to college basketball",
        "url": "https://www.cbssports.com/college-basketball/news/john-calipari-closing-in-on-agreement-with-arkansas-kentucky-coach-taking-over-razorbacks-is-imminent/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2022/08/12/1ae951f1-3dd9-4edd-9317-1e5a3513fbb1/thumbnail/1200x675/23dd54ce2e4b8360452d402601702674/kentucky-calipari-usatsi.jpg",
        "publishedAt": "2024-04-09",
        "content": "Kentucky coach John Calipari is expected to sign a contract to become the next coach at Arkansas, sources told CBS Sports. Calipari met with multiple Kentucky athletic administrators Monday afternoon… [+4981 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135013,
        "name": "NBCSports.com",
        "author": "Mike Florio",
        "title": "Iowa-South Carolina generates NFL-style audience for women's national championship",
        "description": "Nothing can generate massive live audiences like the NFL.",
        "url": "https://www.nbcsports.com/nfl/profootballtalk/rumor-mill/news/iowa-south-carolina-generates-nfl-style-audience-for-womens-national-championship",
        "urlToImage": "https://nbcsports.brightspotcdn.com/dims4/default/1eab4e7/2147483647/strip/true/crop/6090x3426+0+317/resize/1440x810!/quality/90/?url=https%3A%2F%2Fnbc-sports-production-nbc-sports.s3.us-east-1.amazonaws.com%2Fbrightspot%2F29%2Fca%2Fbc0323d84e83941ead5807907a6d%2Fhttps-delivery-gettyimages.com%2Fdownloads%2F2147015348",
        "publishedAt": "2024-04-09",
        "content": "Nothing can generate massive live audiences like the NFL.\r\nWell, almost nothing.\r\nVia Sports Business Journal, the Iowa-South Carolina womens basketball championship on ABC and ESPN averaged 18.7 mil… [+1141 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135014,
        "name": "CBS Sports",
        "author": "Cameron Salerno",
        "title": "John Calipari to leave Kentucky for Arkansas: Who's in, out on Wildcats roster for 2024-25",
        "description": "Tracking what UK's roster will look like in 2024-25 following Calipari's expected departure",
        "url": "https://www.cbssports.com/college-basketball/news/john-calipari-to-leave-kentucky-for-arkansas-whos-in-out-on-wildcats-roster-for-2024-25/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/03/04/b40e55ca-5365-4b09-b250-f7d95c02dfec/thumbnail/1200x675/e68ca5574fd82afc121be55cd948168c/reed-sheppard-usatsi.jpg",
        "publishedAt": "2024-04-08",
        "content": "In a shocking move, John Calipari is expected to leave for the vacant job at Arkansas and the impact of Calipari departing UK after 15 seasons is bound to have a seismic shift on the coaching carouse… [+9048 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 135015,
        "name": "CBS Sports",
        "author": "Gary Parrish",
        "title": "John Calipari leaving Kentucky for Arkansas is a shining moment for both programs who each get a fresh start",
        "description": "Calipari's move to take over the Razorbacks is the right move for all parties involved",
        "url": "https://www.cbssports.com/college-basketball/news/john-calipari-leaving-kentucky-for-arkansas-is-a-shining-moment-for-both-programs-who-each-get-a-fresh-start/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/08/b5008ce9-2f42-4cbe-b170-3ec4095a1a88/thumbnail/1200x675/7102064e12badd423bf0a2d057b7577f/gettyimages-1200832154-1.jpg",
        "publishedAt": "2024-04-08",
        "content": "GLENDALE, Ariz. -- Only a few things in college basketball could ever overshadow the title game of any NCAA Tournament, especially one as anticipated as what we're getting here inside State Farm Stad… [+3903 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 134252,
        "name": "CBS Sports",
        "author": "Cameron Salerno",
        "title": "John Calipari to leave for Arkansas: Can Wildcats keep 2024's No. 2 recruiting class together?",
        "description": "The Wildcats had the No. 2 recruiting class in the 2024 cycle behind Duke before Calipari interviewed with Arkansas",
        "url": "https://www.cbssports.com/college-basketball/news/john-calipari-to-leave-for-arkansas-can-wildcats-keep-2024s-no-2-recruiting-class-together/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/08/724a4a1b-2a23-4ba9-a932-0ca9d1d9d4cc/thumbnail/1200x675/4c0a8f17cbb1a6d93c03fee236bccf82/gettyimages-2131308277-1.jpg",
        "publishedAt": "2024-04-08",
        "content": "In a shocking move, John Calipari is expected to leave for the vacant job at Arkansas and the impact of Calipari departing UK after 15 seasons is bound to have a seismic shift on the coaching carouse… [+4032 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 134253,
        "name": "CBS Sports",
        "author": "David Cobb",
        "title": "College basketball transfer portal 2024: Ranking top 60 players as Aaron Bradshaw, Oumar Ballo test waters",
        "description": "Here are the best transfers in the portal so far during college basketball's 2024 offseason",
        "url": "https://www.cbssports.com/college-basketball/news/college-basketball-transfer-portal-2024-ranking-top-60-players-as-aaron-bradshaw-oumar-ballo-test-waters/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/08/04e1d356-7b49-4eaf-bdae-75abeb0834d8/thumbnail/1200x675/2bc9b33bbba147924cd49aa42e79de1a/aaron-bradshaw-kentucky-usatsi.jpg",
        "publishedAt": "2024-04-08",
        "content": "The crop of elite big men on the move in college basketball continued to swell Monday with the news that Kentucky center Aaron Bradshaw and Arizona center Oumar Ballo are entering the transfer portal… [+24528 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 134254,
        "name": "CBS Sports",
        "author": "Sam Quinn",
        "title": "2024 NBA playoffs: 16 games this week will help determine almost every postseason seed",
        "description": "There's a week of regular-season action left, and only one seed is locked in",
        "url": "https://www.cbssports.com/nba/news/2024-nba-playoffs-16-games-this-week-will-help-determine-almost-every-postseason-seed/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/08/1b249a85-9720-4e0e-ae5f-6ea74038c639/thumbnail/1200x675/9df06a6e391300c17ab43bf74edb72a0/zion-lebron-usatsi.png",
        "publishedAt": "2024-04-08",
        "content": "With five days of regular-season basketball left on the schedule, only one out of the 20 postseason seeds has been locked in definitively. The Boston Celtics will be the No. 1 seed in the Eastern Con… [+8375 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 134255,
        "name": "CBS Sports",
        "author": "Kyle Boone",
        "title": "Zach Edey vs. Donovan Clingan: How duel of Purdue and UConn's 7-footers will determine national championship",
        "description": "A breakdown of what to expect Monday as two of the biggest stars clash in the NCAA Tournament championship game",
        "url": "https://www.cbssports.com/college-basketball/news/zach-edey-vs-donovan-clingan-how-duel-of-purdue-and-uconns-7-footers-will-determine-national-championship/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2024/04/07/a83e35c7-9246-4c8f-9c3a-0a4acaff71f6/thumbnail/1200x675/338586fcf641d8c0de2f03d16b7044e0/screenshot-2024-04-07-at-2-49-23-pm.png",
        "publishedAt": "2024-04-08",
        "content": "GLENDALE, Ariz. -- College basketball's biggest game of the season will be played Monday night inside State Farm Stadium. So, it's only fitting that on the sport's biggest stage with the biggest poss… [+4900 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    },
    {
        "id": 134256,
        "name": "CBS Sports",
        "author": "CBS Sports Staff",
        "title": "John Calipari reportedly to Arkansas, Kentucky coach candidates: Coaching search, targets by SEC insiders",
        "description": "CatsPause has covered the Wildcats for years and reveals the top coaching Kentucky basketball candidates to replace John Calipari",
        "url": "https://www.cbssports.com/college-basketball/news/john-calipari-reportedly-to-arkansas-kentucky-coach-candidates-coaching-search-targets-by-sec-insiders/",
        "urlToImage": "https://sportshub.cbsistatic.com/i/r/2023/03/12/1fda9ee1-c8f0-4047-8c08-0a3e5715e7bf/thumbnail/1200x675/241bca7569adf8c0a858097d678eb3c2/usatsi-20198259.jpg",
        "publishedAt": "2024-04-08",
        "content": "After 15 years at the helm of one of the blue bloods of the sport, John Calipari is departing his post as the Kentucky Wildcats basketball coach and heading to the Arkansas Razorbacks. Calipari is co… [+4467 chars]",
        "category": "sports",
        "categorySpecified": "nba"
    }
        ],
        start: 0,
        end: 12,
        //trending articles array
        trendingArticles: [],
        //toggling mobile articles
        newsOrTrendingClicked: true,
        //all likes from a current user
        userLikes: [],
        //all dislikes from a current user
        userDislikes: [],
        //all save for laters from a current user
        usersSavedForLater: [],
        //current post/category like whenever thumbs up is pressed, or fire emoji (its lit)
        postLiked: 0,
        categoryLiked: '',
        postClicked: 0,
        //current post dislike whenever thumbs up is pressed
        postDisliked: 0,
        //current saved for later post when book mark is pressed
        saved: 0,
        //current user logged ins id
        currentUser: 0,
        isLiked: false,
        isDisliked: false,
        isSaveForLater: false
      }
    },
    beforeMount() {
      //calls current pages articles
      ArticleService.listNbaArticles().then(response => {
      this.articles = response.data;
    }),
    //calls for current user
    ArticleDataService.getUser().then(response => {
          this.currentUser = response.data
    }),
    //calls all of a users likes
    ArticleDataService.checkPostLike().then(response => {
      this.userLikes = response.data
    }), 
   //calls current users dislikes
   ArticleDataService.listDislikes().then(response => {
      this.userDislikes = response.data
    }),
    //calls current users save for laters
    ArticleDataService.listSaveForLaters().then(response => {
      this.usersSavedForLater = response.data
    }),
    //calls all of the trending articles
    ArticleService.listTrendingArticles().then(response => {
      this.trendingArticles = response.data
    });
  },  
    mounted() {
      //depending on size renders the correct navbar
      this.isDesktop = window.matchMedia("(min-width: 913px)").matches
      window.addEventListener("resize", this.handleResize)
      window.addEventListener('scroll', this.handleScroll)
    },
  computed: {
  //how whether or not a user has liked a post in the past and keeps the buttons a different color
  articleIsLiked() {
    return articleId => this.userLikes.some(like => like.id === articleId || like.article_id === articleId);
  },
  //how whether or not a user has disliked a post in the past and keeps the buttons a different color
  articleIsDisliked() {
    return articleId => this.userDislikes.some(dislike => dislike.id === articleId || dislike.article_id === articleId);
  },
  //how whether or not a user has saved a post in the past and keeps the buttons a different color
  articleIsSavedForLater() {
    return articleId => this.usersSavedForLater.some(saved => saved.id === articleId || saved.article_id === articleId);
  },
  displayArticles(){
    return this.articles.slice(this.start, this.end);
    }, 

},
    methods: {
      //handles resize to toggle navbars
      handleResize() {
        this.isDesktop = window.matchMedia("(min-width: 913px)").matches
        this.newsOrTrendingClicked = true
      },
      handleScroll() {
       let scrollY = window.scrollY;
       let innerHeight = window.innerHeight;
       let docHeight = document.body.offsetHeight;
       if (scrollY + innerHeight >= docHeight * 0.4) {
         this.end+=12
       }
      },
      //likes a post, deletes in sql or posts to sql
      handleLikeClick(){
        if(this.userLikes.some(like => like.article_id === this.postLiked)){
          ArticleDataService.unlikeAPost(this.postLiked).then(response=> {
            if(response.status === 200 ){
              this.userLikes = this.userLikes.filter(like => like.article_id !== this.postLiked);
            
              this.isLiked = false;
            }
          })
        } else {
          let like = {
            "liked": true
          };
          if(this.userDislikes.some(like => like.article_id === this.postLiked)){
            ArticleDataService.unlikeAPost(this.postLiked).then(response=> {
            if(response.status === 200 ){
              this.userDislikes = this.userDislikes.filter(like => like.article_id !== this.postLiked);
             
              this.isLiked = false;
            }
          })
          }
          if(this.usersSavedForLater.some(saved => saved.article_id === this.postLiked)){
            ArticleDataService.removeSaveForLater(this.postLiked).then(response=> {
            if(response.status === 200 ){
              this.usersSavedForLater = this.usersSavedForLater.filter(saved => saved.article_id !== this.postLiked);
           
              this.isLiked = false;
            }
          })
          }
          ArticleDataService.likeAPost(like, this.postLiked).then(response=>{
           
              if(response.status===200){
             
                  this.userLikes.push({
        user_id: this.currentUser,
        article_id: this.postLiked,
        liked: true
      });
                  this.isLiked = true;
              }
          })

          //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: this.postLiked,
            event: `like`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('like logged')
            } else {
              console.log('not working')
            }
          })
      }
    },
    //dislikes a post, deletes in sql or posts to sql
    handleDislikeClick() {
if (this.userDislikes.some(dislike => dislike.article_id === this.postDisliked)) {
  ArticleDataService.undislikeAPost(this.postDisliked).then(response => {
    if (response.status === 200) {
      this.userDislikes = this.userDislikes.filter(dislike => dislike.article_id !== this.postDisliked);
   
      this.isDisliked = false;
    }
  });
} else {
  let Dislike = {
    "disliked": true
  };
  if (this.userLikes.some(like => like.article_id === this.postDisliked)) {
    ArticleDataService.unlikeAPost(this.postDisliked).then(response => {
      if (response.status === 200) {
        this.userLikes = this.userLikes.filter(like => like.article_id !== this.postDisliked);
        
        this.isLiked = false;
      }
    });
  }
  if (this.usersSavedForLater.some(saved => saved.article_id === this.postDisliked)) {
    ArticleDataService.removeSaveForLater(this.postDisliked).then(response => {
      if (response.status === 200) {
        this.usersSavedForLater = this.usersSavedForLater.filter(saved => saved.article_id !== this.postDisliked);
       
        this.isLiked = false;
      }
    });
  }
  ArticleDataService.dislikeAPost(Dislike, this.postDisliked).then(response => {
 
    if (response.status === 200) {
   
      this.userDislikes.push({
        user_id: this.currentUser,
        article_id: this.postDisliked,
        disliked: true
      });
      this.isDisliked = true;
      console.log(this.trendingArticles)
    }
  });

   //log it as an event for the recommender using the event logger
   const event = {
            user_id: this.currentUser,
            articleId: this.postDisliked,
            event: `dislike`,
            sessionId: this.$store.state.sessionId
          }
          
          LoggerService.logger(event).then(response => {
            if(response.status == 200){
              console.log('dislike logged')
            } else {
              console.log('not working')
            }
          })
  
}

},
//saves a post, deletes in sql or posts to sql
      handleSaveForLaterClick(){
        if(this.usersSavedForLater.some(saved => saved.article_id === this.saved)){
          ArticleDataService.removeSaveForLater(this.saved).then(response=> {
            if(response.status === 200 ){
              this.usersSavedForLater = this.usersSavedForLater.filter(saved => saved.article_id !== this.saved);
              
              this.isSaveForLater = false;
            }
          })
        } else {
          let SaveForLater = {
            "saveForLater": true
          };  
          if(this.userLikes.some(like => like.article_id === this.saved)){
          ArticleDataService.unlikeAPost(this.saved).then(response=> {
            if(response.status === 200 ){
              this.userLikes = this.userLikes.filter(like => like.article_id !== this.saved);
           
              this.isLiked = false;
            }
    
          })
        }
        if(this.userDislikes.some(like => like.article_id === this.saved)){
          ArticleDataService.undislikeAPost(this.saved).then(response=> {
            if(response.status === 200 ){
              this.userDislikes = this.userDislikes.filter(like => like.article_id !== this.saved);
            
              this.isLiked = false;
            }
          })
        }
        ArticleDataService.saveForLater(SaveForLater, this.saved).then(response=>{

              if (response.status===200){
               
                  this.usersSavedForLater.push({
                    user_id: this.currentUser,
                    article_id: this.saved,
                    disliked: true
                  });
                  this.isSaveForLater = true;
                }
              });
              //log it as an event for the recommender using the event logger
   const event = {
            user_id: this.currentUser,
            articleId: this.saved,
            event: `save_for_later`,
            sessionId: this.$store.state.sessionId
          }
       
          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('save logged')
            } else {
              console.log('not working')
            }
          })
              }
    },
    //in mobile, this renders the pages articles or the trending articles
    toggleTrendingAndNews(){
          if(this.newsOrTrendingClicked){
          this.newsOrTrendingClicked = false;
          } else {
              this.newsOrTrendingClicked = true;
          }
      },
      logArticleClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: this.postClicked,
            event: `read:article`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('title logged')
            } else {
              console.log('not working')
            }
          })
      },
      pushToArticle(category, title, id){
        let articleTitle = title
        .toLowerCase()
        .replace(/\W+/g, '-')
        .replace(/^-|-$/g, '');
      if(category !== null){
      this.$router.push({
        path: `/${category}/${articleTitle}/${id}`
      })
    } else {
      this.$router.push({
        path: `/business/${articleTitle}/${id}`
      })
    }
    },
    searchArticles(term){
      console.log(term)
      this.$router.push({
        path: `/searchArticles/${term}`,
      })
    }
  },
  
  //checks what size the window is on load
    beforeUnmount() {
      window.removeEventListener("resize", this.handleResize)
    }
  }
  
  
  
  </script>
  
  <style scoped>
  a{
    text-decoration: none;
    color: black;
    transition: all 0.3s ease;
    font-family: 'Titillium Web', sans-serif;
  }
  a:hover{
    color: #3088d4;
  }
  .hidden {
      display: none;
  }

  i {
    margin: 9px 5px 0px 5px;
    color: #0b7ee3;
  }
  i:hover{
    color: #dd3434;
  }

  .i-changed {
    color:#dd3434
  }
  .trending-nav{
    display: none;
  }
  
  button{
    background: none;
    border: none;
    margin: 1em;
    cursor: pointer;
  }

  .search-bar{
    margin-top: 10px;
    background: #ffffff;
border: none;
border: 1px solid #e5e5e5;
color: #262626;
width: calc(100% - 70px);
padding: 8px 35px 8px 23px;
font-size: 13px;
background-position: right 8px;
background-repeat: no-repeat;
border-radius: 60px;
box-shadow: 0px 0px 25px rgba(0,0,0,  0);
}

.fa-magnifying-glass{
        float: right;
        margin-right: 15px;
        margin-top: -18px;
        position: relative;
        z-index: 2;
}
  
  .container {
      display: flex;
      flex-direction: column;
  }
  
  .heading-holder{
    margin-top: 40%;
    margin-left: 9%;
    font-family: 'Titillium Web', sans-serif;
    text-align: left;
    font-size: 50px;
    }

    @media (min-width: 1000px){
      .heading-holder{
        margin-top: 9%;
        font-size: xx-large;
        margin-left: 17%;
      }
    }

    @media (max-width: 750px){
        .heading-holder{
            font-size: 30px;
        }
    }

  .main-area{
    display: flex;
    height: 100%;
  }

  /* TRENDING */

  #trending{
  flex-basis: 16%;
  flex-shrink: 0;
    align-items: center;
    border: 2px solid #c3c5cc;
    font-weight: 900;
}

.trending-articles{
font-family: 'Titillium Web', sans-serif;
font-size: larger;
margin-bottom: 20px;
border-top: .1px solid #3088d4;
background-color: #f4f1f1;
}

.trending-title{
    margin-top: 10px;
    cursor: pointer;
  }
  .trending-published{
    margin: 3px;
    background: #e4e6eb;
    font-size: small;
    text-decoration: none;
  }

  .trending-data{
    display: flex;
    justify-content: space-evenly;
    align-items: baseline;
    margin: 0px;
  }

  .trending-category{
    margin: 0px;
    font-size: medium;
  }

  .trending-like-count{
    margin: 0px;
    font-size: medium;
  }
  
  /* MAIN NEWS GRID */

  .nba-scores{
    width: 250%;
    align-items: center;
  }
  .news-grid{
    height: fit-content;
    width: 100%;
    display: grid;
    grid-template-columns: repeat(4, minmax(250px, 1fr));
    grid-auto-rows: minmax(250px, auto);
    grid-auto-flow: dense;
    padding-top: 15px;
    gap: 5px;
    background-color: #eeeff1;
    align-content: center;
  }

@media (min-width: 650px) and (max-width: 916px) {
.news-grid {
  grid-template-columns: 1fr 1fr 1fr;
}
}

@media (max-width: 649px) {
.news-grid {
  grid-template-columns: 1fr 1fr;
  gap: 15px;
  align-items: center;
  width: auto;

}

@media (min-width: 100px) and (max-width: 913px) {
    /* TRENDING ON MOBILE */
  
    /* TRENDING NAV BOTTOM BAR */
    /* DISPLAYED AT 2 COLUMN LAYOUT BELOW */
  
    #trending{
      display: none;
    }
    .trending-nav{
      display: none;
      background-color: #fff;
      z-index: 1;
      position: fixed;
      bottom: 0;
      width: 100%;
      
    }
  
    .trending-nav{
      display: flex;
      justify-content: space-around;
      border-top: 2px solid #0b7ee3;
      border-radius: 4%;
    }
  
    .news-grid-trending{
      width: 100vw;
      height: fit-content;
      padding: 3% 3% 3% 3%;
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 15px;
      padding: 3vw 4vw 3vw 0vw;
      grid-auto-flow: dense;
      gap: 20px;
      background-color: #eeeff1;
      align-content: center;
    }
    .trending-nav:hover{
      border-top: 2px solid #dd3434;
    }
    .trending-nav-list{
      display: flex;
      justify-content: space-evenly;
      list-style-type: none;
    }
    .trending-nav-news-button-clicked{
      color: #dd3434;
    }
    .trending-nav-search-button:hover{
      color: #dd3434;
    }
  
    .trending-nav-trending-button:hover{
      color: #dd3434;
    }

    .trending-nav-profile-button:hover{
      color: #dd3434;
    }
  }


  .heading-holder {
  text-align: left;
  margin-left: 3%;
  
}

.article{
    width: 80%;
  }


  

}


  /* ARTICLE */

  .article{
    padding-bottom: 20px;
    border-bottom: 1px solid #dd3434;
    margin-left: 5%;
    height: fit-content;
    width: 80%;
    background-color: #fff;
    transition: all 0.3s ease;
    animation: fadeInBottom 0.5s ease-in;
  }
  
  @keyframes fadeInBottom {
    from {
        transform: translateY(100%);
        opacity: 0;
    }
    to {
        transform: translateY(0);
        opacity: 1;
    }
}


  .article:hover,
  .article.active{
    border-bottom: 2px solid #0b7ee3;
    background-color: #fff;
    color: #3088d4;
    transition: border-bottom 0.3s ease, color 0.3s ease;
    transform: scale(1.01);
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    opacity: 1;
  }

  .article-image{
    height: 57%;
    width:100%;

  }

  .title{
    margin: 0px;
    cursor: pointer;
  }
  @media (max-width: 630px){
    .title{
      font-size: larger;
    }
  }
  @media (max-width: 916px){
  .buttons{
    display: flex;
    justify-content: space-evenly;
  }
}
  
  .category{
    font-size: x-small;
    display: inline-block;
    margin: 0px 0px 10px 0px;
    z-index: 1;
    background: #e4e6eb;
    border-radius: 10%;
    padding: 2px;
    text-align: left;
  }
  .toggle-main-content-right{
    z-index: 1;
    margin-left: 95.4%;
    margin-top: 50%;
    position: fixed;
    background-color: #fff;
    border-radius: 50%;
  }

  .fa-arrow-right:before{
    background-color: #fff;
    border-radius: 50%;
  }
  .toggle-main-content-left{
    z-index: 1;
    margin-right: 95.4%;
    margin-top: 50%;
    position: fixed;
    background-color: #fff;
    border-radius: 50%;
  }

  .fa-arrow-left:before{
    background-color: #fff;
    border-radius: 50%;
  }

  </style>
