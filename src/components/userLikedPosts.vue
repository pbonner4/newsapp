<template>
    <div class="liked-posts-container">
<h1 v-on:click="showLikes = true" v-show="showLikes == false">Show Liked Posts <i class="fa-solid fa-caret-right"></i></h1>
                <h1 v-on:click="showLikes = false" v-show="showLikes == true">Hide Liked Posts <i class="fa-solid fa-caret-down"></i></h1>
        <div class = "liked-article-container" v-show="showLikes == true">
                <!-- all articles in current subject data -->
                <div class = "article" v-for="article in displayArticles" v-bind:key="article.id">
                  <div>
                    <img class="article-image" v-lazy=article.urlToImage alt="article-image" preload="auto">
                    <h3 class = "category">published at: {{ article.publishedAt }}</h3>
                  </div>
                  <a v-on:click="pushToArticle(article.id)">
                    <h2 class="title" v-on:click="postClicked=article.id; logArticleClick()">{{article.title}}</h2>
                  </a>
                  <!-- likes dislikes and save for later buttons -->
                  <div class = "buttons">
                    <button v-on:click="postLiked = article.id; handleLikeClick(article.id)">
                      <i class="fa fa-thumbs-up fa-regular" v-bind:class="{'i-changed': articleIsLiked(article.id)}">
                      </i></button>
                  </div>
                </div>
            </div>
            <div class="show-more-button" v-on:click="end = userLikesLaterLength" v-if="end == 6 && showLikes == true"> 
          <h4 >Show More </h4> <i class="fa fa-solid fa-caret-down" style="color: #3b9aed" v-on:click="end = userLikesLaterLength"></i>
        </div>
        </div>
    </template>
    
    <script>
    
    import ArticleService from '../services/ArticleService';
    import ArticleDataService from '../services/ArticleDataService';
    import LoggerService from '@/services/LoggerService';
  
    export default {
      name: "likedPosts",
      components: {
      },
      data() {
        return {
            articles: [],
        userLikes: [],
          postLiked: 0,
          //current user logged ins id
          currentUser: 0,
          //for showing the likes header
          showLikes: false,
          isLiked: false,
          isDisliked: false,
          isSaveForLater: false,
          start: 0,
          end: 6
        }
      },

      async mounted() {
      //calls for current user
      ArticleDataService.getUser().then(response => {
            this.currentUser = response.data
      }),
      //calls all of a users likes
      ArticleDataService.checkPostLike().then(response => {
        this.userLikes = response.data
      }), 
      ArticleService.getAllArticles().then(response => {
        this.articles = response.data
      })
    },  
    computed: {
      //how whether or not a user has liked a post in the past and keeps the buttons a different color
    articleIsLiked() {
      return articleId => this.userLikes.some(like => like.id === articleId);
    },
    displayArticles() {
      return this.userLikes.slice(this.start, this.end)
    },
    userLikesLaterLength(){
      return this.userLikes.length()
    }

  },
      methods: {
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
                alert('dislike logged')
              } else {
                alert('not working')
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
  
                alert('save logged')
              } else {
                alert('not working')
              }
            })
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
  
                alert('title logged')
              } else {
                alert('not working')
              }
            })
        },
        pushToArticle(id){
          this.$store.commit('setContentId', id);
          this.$router.push({
            path: `/nfl/${this.$store.state.articleContentId}`
          })
        },
    },
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
      cursor: pointer;
    }
    i:hover{
      color: #dd3434;
    }
  
    .i-changed {
      color:#dd3434
    }
  
    button{
      background: none;
      border: none;
      margin:5px;
      cursor: pointer;
    }
    /* ARTICLE */

    .liked-posts-container{
        width: 100%;
        background-color: #eeeff1;
    }
    .liked-article-container{
        margin: 0px;
        width: 90%;
      height: fit-content;
      padding: 3% 3% 3% 3%;
      display: grid;
      grid-template-columns: repeat(4, minmax(250px, 1fr));
      grid-auto-rows: minmax(250px, auto);
      grid-auto-flow: dense;
      gap: 20px;
    }
  
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
  
    .show-more-button{
  display: flex;
  align-items: center;
  justify-content: center;
  height: fit-content;
  margin: 0px;
}

.show-more-button > i{
  margin: 0px;
}

    </style>