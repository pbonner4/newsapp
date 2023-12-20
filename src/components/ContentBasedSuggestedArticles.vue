<template>
  <div>
    <h2>You May Also Read:</h2>
    <div class="suggested-articles-container" > 
        <div class="suggested-article" v-for="article in articlesToSuggest" v-bind:key="article.id" v-on:click="pushToArticle(article.categorySpecified, article.title, article.id)">
        <h2>{{ article.title }}</h2>
        <img class="article-image" v-lazy=article.urlToImage alt="article-image"> 
        </div>
    </div>
    
  </div>
</template>

<script>
import ArticleService from '@/services/ArticleService';

export default {
    props: ['articleId'],
    data(){
        return {
            suggestedArticles: []
        }
    },
    async mounted(){
        this.fetchSuggestedArticles();
    },
    beforeRouteUpdate(to, from, next) {
        ArticleService.listSuggestedContentBasedArticles(this.articleId).then(response => {
            
            this.suggestedArticles = response.data
            console.log(JSON.stringify(this.suggestedArticles))
        }).catch(error => {
    console.error(error);
    next(error);
  });
},
watch: {
    $route() {
    this.fetchSuggestedArticles();
  }
    },
    computed: {
        articlesToSuggest(){
            return this.suggestedArticles.slice(0,6)
        },
    },
    methods: {
        async fetchSuggestedArticles() {
            try {
                const response = await ArticleService.listSuggestedContentBasedArticles(this.articleId);
                this.suggestedArticles = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        logId(id){
          console.log(id)
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
    }
    }
}
</script>

<style scoped>

h2{
    width: fit-content;
    margin: .5em;
}

.article-image{
    height: 100%;
    width: 80%;
}
.suggested-articles-container{
    width: 90%;
    display: grid;
    margin: 1em;
    grid-template-columns: repeat(3, minmax(250px, 1fr));
    grid-auto-rows: minmax(250px, auto);
    align-content: center;
    align-items: center;
    font-size: small;
    font-family: Arial, Helvetica, sans-serif;
}
@media (max-width: 916px){
    .suggested-articles-container{
    grid-template-columns: repeat(2, minmax(250px, 1fr));
    }
}
@media (max-width: 650px){
    .suggested-articles-container{
    grid-template-columns: repeat(1, minmax(250px, 1fr));
    }
}
.suggested-article{
    background-color: #eeeff1;
    align-items: center;
    margin: 1em;
    border: .6px solid #b6b8b9;

}

.suggested-article:hover,
  .suggested-article.active{
    color: #3088d4;
    transition: border-bottom 0.3s ease, color 0.3s ease;
    transform: scale(1.01);
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    opacity: 1;
    cursor: pointer;
  }



</style>