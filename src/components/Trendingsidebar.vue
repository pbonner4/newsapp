<template>
            <div>
              <div class = "trending-articles"  v-for="articles in trendingArticles" v-bind:key="articles.articleId">
                <div class = "trending-article-grid">
                  <div class="trending-image-section">
                    <a v-bind:href=articles.url target="_blank">
                <img class="trending-article-image" :src=articles.urlToImage alt="trending-article-image">
              </a>
              </div>
              <div class="trending-information-section">
                <div class="trending-title-container">
                  <a v-bind:href=articles.url target="_blank"> 
                    <h4 class = "trending-title">{{articles.title}}</h4>
                  </a>
                </div>
                <h2 class = "trending-published">On: {{ articles.publishedAt }}</h2>
            </div>
              </div>
              <div class="trending-data">
              <h2 class = "trending-category">{{ articles.category }}</h2>
                <h2 class = "trending-like-count">Likes<button v-on:click="postLiked = articles.articleId; handleLikeClick(articles.articleId)"><i class="fa fa-fire" v-bind:class="{'i-changed': articleIsLiked(articles.articleId)}"></i></button>: {{ articles.likeCount }}</h2>
              </div>
            </div>
          </div>
</template>

<script>

    import ArticleService from '../services/ArticleService';
    import ArticleDataService from '../services/ArticleDataService';

export default {
  name: "trendingSidebar",

data(){
  return {
    trendingArticles: [],
    userLikes: [],
    currentUser: 0,
    isLiked: false,
    postLiked: 0,
  }
},
computed: {
  articleIsLiked() {
      return articleId => this.userLikes.some(like => like.article_id === articleId);
    },
},
methods:{
  handleLikeClick(){
          if(this.userLikes.some(like => like.article_id === this.postLiked)){
            ArticleDataService.unlikeAPost(this.postLiked).then(response=> {
              if(response.status === 200 ){
                this.userLikes = this.userLikes.filter(like => like.article_id !== this.postLiked);
                console.log('unliked')
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
                console.log('unliked')
                this.isLiked = false;
              }
            })
            }
            if(this.usersSavedForLater.some(saved => saved.article_id === this.postLiked)){
              ArticleDataService.removeSaveForLater(this.postLiked).then(response=> {
              if(response.status === 200 ){
                this.usersSavedForLater = this.usersSavedForLater.filter(saved => saved.article_id !== this.postLiked);
                console.log('unsaved')
                this.isLiked = false;
              }
            })
            }
            ArticleDataService.likeAPost(like, this.postLiked).then(response=>{
              console.log("hello")
                if(response.status===200){
                    console.log("liked")
                    this.userLikes.push({
          user_id: this.currentUser,
          article_id: this.postLiked,
          liked: true
        });
                    this.isLiked = true;
                }
            })
        }
      },
  
},
created() {
  ArticleService.listTrendingArticles().then(response => {
        this.trendingArticles = response.data
      }),
      ArticleDataService.getUser().then(response => {
            this.currentUser = response.data
      });
},
}
</script>
