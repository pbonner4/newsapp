<template>
    <div> 
        <div>
            <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
        </div>
        <h1 class="heading-holder">Trending <i class="fa fa-fire"></i> </h1>
        <div class = "news-grid-trending">
              <div class = "article" v-for="article in trendingArticles" v-bind:key="article.articleId">
                <div>
                  <img class="article-image" v-lazy=article.urlToImage alt="article-image" preload="auto">
                  <h3 class = "category">published at: {{ article.publishedAt }}</h3>
                </div>
                <a  v-on:click ="pushToArticle(article.categorySpecified, article.title, article.id)">
                  <h2 class="title" v-on:click="postClicked=article.id; logArticleClick()">{{article.title}}</h2>
                </a>
                <!-- buttons that like dislike and save for later on mobile trending-->
                <div class = "buttons">
                  <button v-on:click="postLiked = article.id; handleLikeClick(article.articleId)"><i class="fa fa-thumbs-up fa-regular" v-bind:class="{'i-changed': articleIsLiked(article.articleId)}"></i><p class = "trending-like-count">{{ article.likeCount }}</p></button> 
                  <button v-on:click="postDisliked = article.id; handleDislikeClick(article.articleId)"><i class="fa-regular fa-thumbs-down" v-bind:class="{'i-changed': articleIsDisliked(article.articleId)}"></i></button>
                  <button v-on:click="saved = article.id; handleSaveForLaterClick(article.articleId)"><i class="fa fa-regular fa-bookmark" v-bind:class="{'i-changed': articleIsSavedForLater(article.articleId)}"></i></button>
                </div>
              </div>
            </div>
            
            <BottomNavigation></BottomNavigation>
    </div>

</template>

<script>
import ArticleDataService from '../services/ArticleDataService'
import ArticleService from '../services/ArticleService'
import LoggerService from '../services/LoggerService'
import BottomNavigation from '@/components/BottomNavigation.vue'
import MobileNavbar from '@/components/MobileNavbar.vue'


export default {
    name: "TrendingPage",
    components: {
        BottomNavigation,
        MobileNavbar
    },
    data(){
        return {
            trendingArticles: [],
            isDesktop: false,
            userLikes: [],
            userDislikes: [],
            usersSavedForLater: []
        }
    },
    
    beforeMount(){
        ArticleService.listTrendingArticles().then(response => {
            this.trendingArticles = response.data
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
    })
    },
    computed: {
         //how whether or not a user has liked a post in the past and keeps the buttons a different color
    articleIsLiked() {
      return articleId => this.userLikes.some(like => like.id === articleId);
    },
    //how whether or not a user has disliked a post in the past and keeps the buttons a different color
    articleIsDisliked() {
      return articleId => this.userDislikes.some(dislike => dislike.id === articleId);
    },
    //how whether or not a user has saved a post in the past and keeps the buttons a different color
    articleIsSavedForLater() {
      return articleId => this.usersSavedForLater.some(saved => saved.id === articleId);
    }
    },
    methods: {
        handleResize() {
          this.isDesktop = window.matchMedia("(min-width: 913px)").matches
          this.newsOrTrendingClicked = true
        },
        pushToArticle(category, title, id){
      let articleTitleNoSpaces = title.replace(/ /g, '-');
      let articleTitle = articleTitleNoSpaces.replaceAll(/[:;,.']/g, "").toLowerCase()
      
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
    },
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
      }
    }
</script>



<style scoped>

button{
    background: none;
    border: none;
    margin: 1em;
    cursor: pointer;
    display: flex;
    align-items: center;
}


i {
    background: none;
    border: none;
    cursor: pointer;
    color: #0b7ee3;
}

.trending-nav-news-button,fa-fire{
    margin: 9px 5px 0px 5px;
    color: #0b7ee3;
}

.fa-fire{
    margin: 9px 5px 0px 5px;
    color: #0b7ee3;
}

.heading-holder{
    margin-top: 40%;
    margin-left: 9%;
    font-family: 'Titillium Web', sans-serif;
    text-align: left;
    font-size: 50px;
    }

    @media (max-width: 750px){
        .heading-holder{
            font-size: 30px;
        }
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

.article{
      padding-bottom: 20px;
      border-bottom: 1px solid #dd3434;
      margin-left: 10%;
      height: fit-content;
      background-color: #fff;
      transition: all 0.3s ease;
      animation: fadeInBottom 0.5s ease-in;
    }

.article-image{
      height: 57%;
      width:100%;
    }

.title{
      margin: 0px;
      cursor: pointer;
      text-decoration: none;
    color: black;
    transition: all 0.3s ease;
    font-family: 'Titillium Web', sans-serif;
    }

.i-changed {
      color:#dd3434
    }

.buttons{
    display: flex;
    justify-content: space-evenly;
  }

  .hidden {
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

    .category{
        font-size: x-small;
    }

</style>