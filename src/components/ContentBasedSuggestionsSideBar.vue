<template>
  <div>
    <div id = "content-suggestions">
              <h2 class="header">Based On What You Liked:</h2>
              <!-- for loop to iterate through the trending articles array -->
            <div class = "content-articles"  v-for="articles in displayArticles" v-bind:key="articles.articleId">
            <div class="content-information-section">
              <div class="content-title-container">
              
                <div> 
                  <img :src="  articles.urlToImage "/>
                </div>
                <a  v-on:click ="pushToArticle(articles.categorySpecified, articles.title, articles.id)">
                  <h4 class = "content-title" v-on:click="postClicked=article.id; logArticleClick()">{{articles.title}}</h4>
                </a>
          </div>
            </div>
            <div class="content-data">
            <h2 class = "content-category">{{ articles.category }}</h2>
            <!-- only going to show likes for trending articles -->
            <h2 class = "content-published">On: {{ articles.publishedAt }}</h2>
          </div>
          </div>
       
            </div>
  </div>
</template>

<script>

import ArticleService from '@/services/ArticleService';
import LoggerService from '@/services/LoggerService';

export default {

  data(){
    return {
      contentBasedSuggestions: [],
      postClicked: 0,
      start: 0,
      end: 8
    }
  },
  async mounted(){
    //calls current pages articles
    ArticleService.listContentBasedArticles().then(response => {
      this.contentBasedSuggestions = response.data;
    }),
    window.addEventListener('scroll', this.handleScroll)
  },
  computed: {
    displayArticles(){
      return this.contentBasedSuggestions.slice(this.start, this.end);
    }
  },
  methods: {
    handleScroll() {
     let scrollY = window.scrollY;
     let innerHeight = window.innerHeight;
     let docHeight = document.body.offsetHeight;
     if (scrollY + innerHeight >= docHeight * 0.7) {
       this.end+=12
     }
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
  }

}
</script>

<style scoped>

img {
  height:  50%;
  width:  60%;
}

#content-suggestions{
  background-color: #f4f1f1;
  width: 20rem;
  align-items: center;
}

@media (max-width: 916px){
    #content-suggestions{
      width: 100%;
    }
  }

.header{
  height: 100%;
  margin: 0px;
  background-color: #fff;
  border: 1px solid #aaa8a8;
  font-family: 'Titillium Web', sans-serif;
}

.content-articles{
  border: 1px solid #aaa8a8;
  padding: 1em;
  flex-basis: 20%;
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
.content-articles:hover,
  .content-articles.active{
    color: #3088d4;
    transition: border-bottom 0.3s ease, color 0.3s ease;
    transform: scale(1.01);
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    opacity: 1;
    cursor: pointer;
  }

.content-title-container{
  display: flex;
  align-items: center;
  margin: 0px;
  font-family: 'Titillium Web', sans-serif;
  font-size: larger;
  font-weight: bolder;
  flex-direction: column;
  height: 90%;
  width: 100%;
}

.content-title{
  margin: 0px;
}

@media (max-width: 916px){
  .content-title{
    font-size: 2rem;
  }
}

.content-data{
  display: flex;
  flex-direction: row;
  align-content: center;
  justify-content: space-evenly;
  background-color: #c0ddf7;
}

@media (max-width: 916px){
  .content-data{
    font-size: 2rem;
  }
}

.content-category{
  margin: 0px;
  font-size:medium;
}

.content-published{
  margin: 0px;
  font-size:medium;
}


</style>