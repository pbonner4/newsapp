<template>
    <div class="container">
        <div>
            <navBar v-bind:class="{hidden: !isDesktop}"/>
            <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
        </div>
        <div>
          <!-- conditional rendering in mobile view -->
            <div class = "comingsoon-intheaters">
                <h1 class="heading-holder" >
                    <div v-on:click="nowOrComingSoon = false" v-bind:class="{grayedHeader: nowOrComingSoon}">In Theaters |</div> 
                    <div v-on:click="nowOrComingSoon = true" v-bind:class="{grayedHeader: !nowOrComingSoon}">| Coming Soon </div> 
                    <i class="fa fa-solid fa-film"></i>
                </h1>
            </div>
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
                  <a v-on:click="pushToArticle(articles.articleId)">
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
              <!-- main news grid, v-if relates to the buttons press in mobile view -->
              <div class = "news-grid">
                <!-- all articles in currect subject data -->
                <div class = "article" v-for="movie in displayInTheaters" v-bind:key="movie.id" v-show="!nowOrComingSoon" v-on:click="handleSummary(movie.id)">
                  <div v-show="selectedSummary != movie.id">
                    <img class="article-image" :src="'https://image.tmdb.org/t/p/w500'+movie.poster_path" alt="movie-image" preload="auto">
                    <h3 class = "category">Release Date: {{ movie.realease_date }}</h3>
                  </div>
                    <h2 class="title" v-show="selectedSummary != movie.id">{{movie.title}}</h2>
                  <!-- ratings buttons -->
                  <div class = "buttons">
                    <button v-show="movie.vote_count == 0">(Not Enough Ratings)
                    </button>
                    <button v-show="movie.vote_count > 0"> Audience Score: {{ movie.vote_average }} / 10
                        <i class="fa fa-solid fa-star" v-show="movie.vote_average >= 8"></i>
                        <i class="fa fa-solid fa-star-half-stroke" v-show="movie.vote_average >= 5 && movie.vote_average <= 7"></i>
                        <i class="fa fa-regular fa-star" v-show="movie.vote_average < 5"></i>
                    </button>
                    <div class="summary" v-show="selectedSummary === movie.id" v-bind:class="{ 'show': selectedSummary === movie.id }">
                      <iframe class="loading-gif" width="100%" height="315" :src="movie.trailer" frameborder="0" allowfullscreen></iframe>
                        <h2>{{ movie.overview }}</h2>
                    </div>

                  </div>
                </div>
                <div class = "article" v-for="movie in displayComingSoon" v-bind:key="movie.id" v-show="nowOrComingSoon" v-on:click="handleSummary(movie.id)">
                  <div v-show="selectedSummary != movie.id">
                    <img class="article-image" :src="'https://image.tmdb.org/t/p/w500'+movie.poster_path" alt="movie-image" preload="auto">
                    <h1 class = "category">Release Date: {{ movie.realease_date }}</h1>
                  </div>
                    <h2 class="title" v-show="selectedSummary != movie.id">{{movie.title}}</h2>
                  <!-- likes dislikes and save for later buttons -->
                  <div class = "buttons">
                    <button v-show="movie.vote_count == 0">(Not Enough Ratings)
                    </button>
                    <button v-show="movie.vote_count > 0"> Audience Score: {{ movie.vote_average }} / 10
                        <i class="fa fa-solid fa-star" v-show="movie.vote_average >= 8"></i>
                        <i class="fa fa-solid fa-star-half-stroke" v-show="movie.vote_average >= 5 && movie.vote_average <= 7"></i>
                        <i class="fa fa-regular fa-star" v-show="movie.vote_average < 5"></i>
                    </button>
                    <div v-show="selectedSummary === movie.id">
                        <h2>
                          <iframe class="loading-gif" width="100%" height="315" :src="movie.trailer" frameborder="0" allowfullscreen loading="lazy"></iframe>
                            {{ movie.overview}}
                        </h2>
                    </div>
                  </div>
                </div>
              </div>
              
              
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
    import BottomNavigation from '@/components/BottomNavigation.vue';
  
    export default {
      name: "inTheaters",
      components: {
        navBar,
        MobileNavbar,
        BottomNavigation
      },
      data() {
        return {
        // //crsf token
        // crsf: '',
        //navbar toggle data property
        isDesktop: false,
        //articles array
        inTheaters: [],
        comingSoon: [],
        nowOrComingSoon: false,
        showSummary: false,
        selectedSummary: 0,
        start: 0,
        end: 5,
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
        loading: true
      }
      },
      beforeMount() {
        //calls movies in theaters
        ArticleService.listIntheaters().then(response => {
            this.inTheaters = response.data
        })
        //calls movies coming soon
        ArticleService.listComingSoon().then(response => {
            this.comingSoon = response.data
        })
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
        this.usersSavedForLater = response.data
      }),
      //calls current users save for laters
      ArticleDataService.listSaveForLaters().then(response => {
        this.userDislikes = response.data
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
    displayInTheaters(){
    return this.inTheaters.slice(this.start, this.end);
    },  
    displayComingSoon(){
    return this.comingSoon.slice(this.start, this.end);
    }, 
  
  },
  methods: {
    iframeLoaded(){
      console.log("loaded")
      this.loading = false;
    },
    handleSummary(movieId){
    if(this.selectedSummary == movieId){
        this.selectedSummary = 0
        return null
    }
        this.selectedSummary = movieId
        if(this.showSummary == false){
            this.showSummary = true
        } else {
            this.showSummary = false
        }
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
       if (scrollY + innerHeight >= docHeight * 0.8) {
         this.end+=4
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
      pushToArticle(id){
        this.$store.commit('setContentId', id);
        this.$router.push({
          path: `/mma/${this.$store.state.articleContentId}`
        })
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

    .grayedHeader{
        color:lightgray;
        cursor: pointer;
    }
.summary {
  opacity: 0;
  transition: opacity 1s ease-in-out;
  height: 0;
}

.summary.show {
  opacity: 1;
  height: auto;
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
    
    .container {
        display: flex;
        flex-direction: column;
    }
    
    .heading-holder{
        margin-top: 150px;
        margin-left: 16%;
        font-family: 'Titillium Web', sans-serif;
        text-align: left;
        display: flex;
        align-items: center;
    }



    @media (max-width: 916px) {
  .heading-holder {
    margin-top: 270px;
    font-size: x-large;
  }
}


    @media (max-width: 750px) {
  .heading-holder {
    margin-top: 170px;
    font-size: x-large;
  }

  .comingsoon-intheaters{
    display: flex;
    flex-direction: column;
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
    .news-grid{
      width: 100vw;
      height: fit-content;
      padding: 3% 3% 3% 3%;
      display: grid;
      grid-template-columns: repeat(4, minmax(250px, 1fr));
      grid-auto-rows: minmax(250px, auto);
      grid-auto-flow: dense;
      gap: 20px;
      background-color: #eeeff1;
      align-content: center;
    }
    @media (min-width: 650px) and (max-width: 916px) {
.news-grid {
  grid-template-columns: 1fr 1fr;
}
}

@media (max-width: 649px) {
.news-grid {
  grid-template-columns: 1fr;
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
  
    
  
  }
  
  
    /* ARTICLE */
  
    .article{
    cursor: pointer;
    padding-bottom: 20px;
    border-bottom: 1px solid #dd3434;
    margin-left: 5%;
    height: fit-content;
    width: 90%;
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

  .comingsoon-intheaters{
    display: flex;
    flex-direction: row;
  }

  .loading-gif{
    background: url("https://media.tenor.com/k9yAts9ymaIAAAAM/loading-load.gif");
    background-repeat: no-repeat;
    background-size: 100% auto; /* 100% width, auto height */
    width: 100%;
  }
  
    </style>