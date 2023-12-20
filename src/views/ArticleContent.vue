<template>
  <div> 
    <div class="container">
        <div>
            <navBar v-bind:class="{hidden: !isDesktop}"/>
            <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
        </div>
        <div v-for="content in article" v-bind:key="content.id">
          <!-- conditional rendering in mobile view -->
            <h1 class="heading-holder" v-if="newsOrTrendingClicked">Date, comments, {{`${content.category} | ${content.categorySpecified}`}}</h1>
            <h1 class="heading-holder" v-if="!newsOrTrendingClicked">Trending <i class="fa fa-fire"></i> </h1>
            <div class="main-area">
              <!-- Trending Sidebar -->
              <div id = "trending">
                <h2>TRENDING ARTICLES</h2>
                <!-- for loop to iterate through the trending articles array -->
              <div class = "trending-articles"  v-for="articles in trendingArticles" v-bind:key="articles.articleId">
              <div class="trending-information-section">
                <div class="trending-title-container">
                  <a  v-on:click ="pushToArticle(articles.categorySpecified, articles.title, articles.articleId)">
                    <h4 class = "trending-title" v-on:click="postClicked=article.articleId; logArticleClick()">{{articles.title}}</h4>
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
              <div class = "news-article">
                <div v-for="content in article" v-bind:key="content.id" >
                  
                  

                    <h1 class="article-title">{{ content.title }}</h1>
                    <div class="share-buttons"> 
                    <ShareNetwork
                  class="twitter-share share-button"
                  network="twitter"
                  :url= " currentUrl "
                  :media="content.urlToImage"
                  :title=" content.title "
                  :hashtags="content.category" 
                  >
                  <i class="fa-brands fa-twitter"></i>
                  Share on Twitter
                  </ShareNetwork>

                    <ShareNetwork
                  class="facebook-share share-button"
                  network="facebook"
                  :url= " currentUrl "
                  :title=" content.title "
                  :description=" content.text.slice(3, 100) + '...' "
                  :hashtags="content.category"
                  >
                  <i class="fa-brands fa-facebook"></i>
                  Share on Facebook
                  </ShareNetwork>

                  <ShareNetwork
                  class="reddit-share share-button"
                  network="reddit"
                  :url= " currentUrl "
                  :media="content.urlToImage"
                  :title=" content.title "
                  :hashtags="content.category" 
                  >
                  <i class="fa-brands fa-reddit-alien"></i>
                  Share on Reddit
                  </ShareNetwork>

                  <ShareNetwork
                  class="linkedin-share share-button" 
                  network="linkedin"
                  :url= " currentUrl "
                  :media="content.urlToImage"
                  :title=" content.title "
                  :hashtags="content.category" 
                  >
                  <i class="fa-brands fa-linkedin"></i>
                  Share on Linkedin
                  </ShareNetwork>

                  <ShareNetwork
                  class="telegram-share share-button"
                  network="telegram"
                  :url= " currentUrl "
                  :title=" content.title "
                  :description=" content.text.slice(3, 100) + '...' "
                  :hashtags="content.category"
                  >
                  <i class="fa-brands fa-telegram"></i>
                  Share via Telegram
                  </ShareNetwork>

                  <ShareNetwork
                  class="sms-share share-button"
                  network="sms"
                  :url= " currentUrl "
                  :title=" content.title "
                  >
                  <i class="fa-solid fa-comment-sms"></i>
                  Share via Text
                  </ShareNetwork>

                  <ShareNetwork
                  class="email-share share-button"
                  network="email"
                  :url= " currentUrl "
                  :title=" content.title "
                  :description=" content.text.slice(3, 100) + '...' "
                  :hashtags="content.category"
                  >
                  <i class="fa-solid fa-inbox"></i>
                  Share via Email
                  </ShareNetwork>
                  </div>
                    <img class="article-image" :src=content.urlToImage>
                    <h4>read from the original source here:</h4> <a class="a-article" v-bind:href="content.url" target="__blank"> {{ content.url }}</a>
                    <GeneratedSummary></GeneratedSummary>
                    <Tweet></Tweet>

                    <div v-html="content.text" ></div>
                    <ContentBasedSuggestedArticles :articleId="content.article_id"></ContentBasedSuggestedArticles>

                    <div class="share-buttons"> 
                    <ShareNetwork
                  class="twitter-share share-button"
                  network="twitter"
                  :url= " currentUrl "
                  :media="content.urlToImage"
                  :title=" content.title "
                  :hashtags="content.category" 
                  >
                  <i class="fa-brands fa-twitter"></i>
                  Share on Twitter
                  </ShareNetwork>

                    <ShareNetwork
                  class="facebook-share share-button"
                  network="facebook"
                  :url= " currentUrl "
                  :title=" content.title "
                  :description=" content.text.slice(3, 100) + '...' "
                  :hashtags="content.category"
                  >
                  <i class="fa-brands fa-facebook"></i>
                  Share on Facebook
                  </ShareNetwork>

                  <ShareNetwork
                  class="reddit-share share-button"
                  network="reddit"
                  :url= " currentUrl "
                  :media="content.urlToImage"
                  :title=" content.title "
                  :hashtags="content.category" 
                  >
                  <i class="fa-brands fa-reddit-alien"></i>
                  Share on Reddit
                  </ShareNetwork>

                  <ShareNetwork
                  class="linkedin-share share-button" 
                  network="linkedin"
                  :url= " currentUrl "
                  :media="content.urlToImage"
                  :title=" content.title "
                  :hashtags="content.category" 
                  >
                  <i class="fa-brands fa-linkedin"></i>
                  Share on Linkedin
                  </ShareNetwork>

                  <ShareNetwork
                  class="telegram-share share-button"
                  network="telegram"
                  :url= " currentUrl "
                  :title=" content.title "
                  :description=" content.text.slice(3, 100) + '...' "
                  :hashtags="content.category"
                  >
                  <i class="fa-brands fa-telegram"></i>
                  Share via Telegram
                  </ShareNetwork>

                  <ShareNetwork
                  class="sms-share share-button"
                  network="sms"
                  :url= " currentUrl "
                  :title=" content.title "
                  :description=" content.text.slice(3, 100) + '...' "
                  :hashtags="content.category"
                  >
                  <i class="fa-solid fa-comment-sms"></i>
                  Share via Text
                  </ShareNetwork>

                  <ShareNetwork
                  class="email-share share-button"
                  network="email"
                  :url= " currentUrl "
                  :title=" content.title "
                  :description=" content.text.slice(3, 100) + '...' "
                  :hashtags="content.category"
                  >
                  <i class="fa-solid fa-inbox"></i>
                  Share via Email
                  </ShareNetwork>
                  </div>

                    <CommentSection :articleId="content.article_id"></CommentSection>
                </div>
              </div>
              <!-- this is to see the trending section in mobile mode, conditional on click of buttons in bottom nav -->
              <div class = "news-grid-trending" v-if="!newsOrTrendingClicked">
                <div class = "article" v-for="article in trendingArticles" v-bind:key="article.articleId">
                  <div>
                    <img class="article-image" v-lazy=article.urlToImage alt="article-image" preload="auto">
                    <h3 class = "category">published at: {{ article.publishedAt }}</h3>
                  </div>
                  
                  <a v-on:click ="pushToArticle(article.categorySpecified, article.title, article.articleId); toggleTrendingAndNews()">
                    <h2 class="title" v-on:click="postClicked=article.articleId; logArticleClick()">{{article.title}}</h2>
                  </a>
                  <!-- buttons that like dislike and save for later on mobile trending-->
                  <div class = "buttons">
                    <button v-on:click="postLiked = article.id; handleLikeClick(article.articleId)"><i class="fa fa-thumbs-up fa-regular" v-bind:class="{'i-changed': articleIsLiked(article.articleId)}"></i><p class = "trending-like-count">{{ article.likeCount }}</p></button> 
                    <button v-on:click="postDisliked = article.id; handleDislikeClick(article.articleId)"><i class="fa-regular fa-thumbs-down" v-bind:class="{'i-changed': articleIsDisliked(article.articleId)}"></i></button>
                    <button v-on:click="saved = article.id; handleSaveForLaterClick(article.articleId)"><i class="fa fa-regular fa-bookmark" v-bind:class="{'i-changed': articleIsSavedForLater(article.articleId)}"></i></button>
                  </div>
                </div>
              </div>
            </div>
        </div>
        <!-- bottom nav bar in mobile mode -->
        <BottomNavigation></BottomNavigation>
    </div>  
  </div>
    </template>
    
    <script>
    
    import navBar from '../components/Navbar.vue';
    import MobileNavbar from '../components/MobileNavbar.vue'
    import ArticleService from '../services/ArticleService';
    import ArticleDataService from '@/services/ArticleDataService';
    import LoggerService from '@/services/LoggerService';
    import CommentSection from '../components/commentsSection.vue';
    import ContentBasedSuggestedArticles from '../components/ContentBasedSuggestedArticles.vue'
    import Tweet from "vue-tweet";
    import BottomNavigation from '@/components/BottomNavigation.vue';
    import GeneratedSummary from '@/components/GenerateSummary.vue'
    import { ShareNetwork } from 'vue-social-sharing'
  
    export default {
      name: "ArticleContent",
      components: {
        navBar,
        MobileNavbar,
        CommentSection,
        Tweet,
        ContentBasedSuggestedArticles,
        BottomNavigation,
        ShareNetwork,
        GeneratedSummary
      },
      data() {
        return {
          // //crsf token
          // crsf: '',
          currentUrl: window.location,
          //navbar toggle data property
          isDesktop: false,
          //amount of articles to show until scroll
          scrollArticles: 1,
          start: 0,
          end: 12,
          //articles array
          articles: [],
          //article content
          article: [],
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
          isSaveForLater: false,
          //to get tweets
          reloadExecuted: false
        }
      },
      created() {
      //calls for current user
      ArticleDataService.getUser().then(response => {
            this.currentUser = response.data
      }),
      //calls all of a users likes
      ArticleDataService.checkPostLike().then(response => {
        this.userLikes = response.data
      }), 
      //gets article Content
      ArticleService.getArticleContent(this.$route.params.id).then(response => {
        this.article = response.data
      }),
      //calls all of the trending articles
      ArticleService.listTrendingArticles().then(response => {
        this.trendingArticles = response.data
      })
    },  
    beforeRouteUpdate(to, from, next) {
  ArticleService.getArticleContent(parseInt(to.params.id)).then(response => {
    this.article = response.data;
    window.scrollTo(0, 0);
    next();
  }).catch(error => {
    console.error(error);
    next(error);
  });
},

      mounted() {
        //depending on size renders the correct navbar
        this.isDesktop = window.matchMedia("(min-width: 913px)").matches
        window.addEventListener("resize", this.handleResize)
        window.addEventListener('scroll', this.handleScroll)
        const script = document.createElement('script');
        script.src = 'https://platform.twitter.com/widgets.js';
        script.async = true;
        document.head.appendChild(script);
      },
    computed: {
      //how whether or not a user has liked a post in the past and keeps the buttons a different color
    articleIsLiked() {
      return articleId => this.userLikes.some(like => like.article_id === articleId);
    },
    //how whether or not a user has disliked a post in the past and keeps the buttons a different color
    articleIsDisliked() {
      return articleId => this.userDislikes.some(dislike => dislike.article_id === articleId);
    },
    //how whether or not a user has saved a post in the past and keeps the buttons a different color
    articleIsSavedForLater() {
      return articleId => this.usersSavedForLater.some(saved => saved.article_id === articleId);
    },
    //articles to display until scroll
    displayArticles(){
      return this.articles.slice(this.start, this.end);
    },
  },
      methods: {
        getTweets(){
        setTimeout(1, window.location.reload())
        
        },
        //handles resize to toggle navbars
        handleResize() {
          this.isDesktop = window.matchMedia("(min-width: 913px)").matches
          this.newsOrTrendingClicked = true
        },
        handleScroll() {
         let scrollY = window.scrollY;
         let innerHeight = window.innerHeight;
         let docHeight = document.body.offsetHeight;
         if (scrollY + innerHeight >= docHeight) {
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
      let articleTitleNoSpaces = title.replace(/ /g, '-');
      let articleTitle = articleTitleNoSpaces.replaceAll(/[:;,.']/g, "").toLowerCase()
      
      if(category !== null){
      this.$router.push({
        path: `/${category}/${articleTitle}/${id}`
      })
      window.reload()
    } else {
      this.$router.push({
        path: `/business/${articleTitle}/${id}`
      })
      window.reload()
    }
    },
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

    :deep(p) {
      text-align: left;
      font-size: x-large;
      
    }
    :deep(.twitter-tweet) {
      margin: auto;
    }

    .a-article{
      text-decoration: none;
      color: #dd3434;
      transition: all 0.3s ease;
      font-family: 'Titillium Web', sans-serif;
    }
  
    :deep PlayerSnippet-info {

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

    .content{
      white-space: pre-wrap; 
      font-size: 30px;
      text-align: left;
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
      margin:5px;
      cursor: pointer;
    }
    
    .container {
        display: flex;
        flex-direction: column;
    }
    
    .heading-holder{
        margin-top: 150px;
        margin-left: 16%;
        font-family: 'Titillium Web', sans-serif;
        text-align: left
        ;
    }
    @media (min-width: 400px) and (max-width: 912px) {
  .heading-holder {
    margin-top: 270px;
  }
  }
  
    .main-area{
      display: flex;
      height: 100%;
    }
  
    /* TRENDING */
  
  #trending{
      flex-basis: 20%;
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
    .news-article{
      width: 100vw;
      height: fit-content;
      padding: 3% 3% 3% 3%;
      background-color: #eeeff1;
      align-content: center;
    }
  
  @media (min-width: 1237px) and (max-width: 1550px) {
  .news-grid {
    grid-template-columns: 1fr 1fr 1fr;
  }
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
      justify-content: center;
      list-style-type: none;
      padding-left: 6vw;
    }
  
    .trending-nav-news-button{
      margin-right: 20vw;
    }
    .trending-nav-news-button-clicked{
      color: #dd3434;
    }
    .trending-nav-search-button{
      margin-left: 25vw;
    }
    .trending-nav-search-button:hover{
      color: #dd3434;
    }
  
    .trending-nav-trending-button{
      margin-right: 0%;
    }
    .trending-nav-trending-button:hover{
      color: #dd3434;
    }
    .trending-nav-profile-button{
      margin-left: 18vw;
    }
    .trending-nav-profile-button:hover{
      color: #dd3434;
    }
  }
  @media (min-width: 100px) and (max-width: 1236px) {
  .news-grid {
    grid-template-columns: 1fr 1fr;
    gap: 15px;
    padding: 3vw 4vw 3vw 0vw;
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
      margin-left: 40px;
      height: fit-content;
      width: 75%;
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

    @media (max-width: 910px){ 
      .article-title{
      font-size: 30px;
      font-weight: bolder;
      text-align: left;
    }
    }
  
    .article-image{
      height: 57%;
      width:90%;
        margin: 1px;
    }
  
    .title{
      margin: 0px;
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

    /* SHARE BUTTONS */

    .share-buttons{
      display: flex;
      flex-wrap: wrap;
      align-items: center;
    }

  .share-button{
    margin: 2px;
  }

  .fa-solid{
    margin: 0px;
  }
  .fa-brands{
    margin: 0px;
  }
    .twitter-share{
      background-color: #00acee;
      padding: 5px;
      color: white;
      border-radius: 10px;
    }

    .twitter-share:hover{
      color: #c3c5cc;
    }

    .twitter-share i{
      color: #fff
    }

    .facebook-share{
      background-color: #1778f2;
      padding: 5px;
      color: white;
      border-radius: 10px;
    }

    .facebook-share:hover{
      color: #c3c5cc;
    }

    .facebook-share i{
      color: #fff
    }
    .reddit-share{
      background-color: #FF4500;
      padding: 5px;
      color: white;
      border-radius: 10px;
    }

    .reddit-share:hover{
      color: #c3c5cc;
    }

    .reddit-share i{
      color: #fff
    }
    .linkedin-share{
      background-color: #0072b1;
      padding: 5px;
      color: white;
      border-radius: 10px;
    }

    .linkedin-share:hover{
      color: #c3c5cc;
    }

    .linkedin-share i{
      color: #fff
    }
    .sms-share{
      background-color: #007AFF;
      padding: 5px;
      color: white;
      border-radius: 10px;
    }

    .sms-share:hover{
      color: #c3c5cc;
    }

    .sms-share i{
      color: #fff
    }

    .telegram-share{
      background-color: #0088cc;
      padding: 5px;
      color: white;
      border-radius: 10px;
    }

    .telegram-share:hover{
      color: #c3c5cc;
    }

    .telegram-share i{
      color: #fff
    }
    .email-share{
      background-color: #dd3434;
      padding: 5px;
      color: white;
      border-radius: 10px;
    }

    .email-share:hover{
      color: #c3c5cc;
    }

    .email-share i{
      color: #fff
    }
    
  
    </style>