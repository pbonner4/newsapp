<template>
    <div> 
      <div>
          <navBar v-bind:class="{hidden: !isDesktop}"/>
          <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
          </div>
        <div class="container" v-bind:class="{hidden: !isDesktop}">

          <div id = "trending">
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
        <img class="loading-gif" v-if="loading" src="https://assets.materialup.com/uploads/b68f4460-aaa9-4e19-99d8-232dfea1c537/preview.gif">
        <div class="desktop-box-score" v-else> 
          <div class="team-names-container"> 
            <div class="team-logo"> 
              <h1 class="team-names" v-bind:class="{grayed: showStats}" v-on:click="showStats = false">{{ boxScore.boxscore.teams[1].team.name }}</h1>
              <img alt="team-logo" :src="boxScore.boxscore.teams[1].team.logo"/>
            </div>
  
            <div class="team-logo"> 
              <h1 class="team-names" v-bind:class="{grayed: !showStats}" v-on:click="showStats = true">{{ boxScore.boxscore.teams[0].team.name }}</h1>
              <img alt="team-logo" :src="boxScore.boxscore.teams[0].team.logo"/>
            </div>
  
          </div>
          <table v-show="showStats">
             <tr>
              <th></th>
              <th v-for="(label, index) in boxScore.boxscore.players[0].statistics[0].labels" :key="index">
                {{ label }}
              </th>
            </tr>
            <tr v-for="(athlete, index) in boxScore.boxscore.players[0].statistics[0].athletes" :key="index">
              <th>
                {{ athlete.athlete.displayName }}
              </th>
              <th v-for="(stat, index) in athlete.stats" :key="index"> 
                {{ stat }}
              </th>
            </tr>
          </table>

          <!-- //Mobile -->
          <table v-show="!showStats ">
             <tr>
              <th></th>
            
              <th v-for="(label, index) in boxScore.boxscore.players[1].statistics[0].labels" :key="index">
                {{ label }}
              </th>
            </tr>
            <tr v-for="(athlete, index) in boxScore.boxscore.players[1].statistics[0].athletes" :key="index">
              <th>
                {{ athlete.athlete.displayName }}
              </th>
              <th v-for="(stat, index) in athlete.stats" :key="index"> 
                {{ stat }}
              </th>
            </tr>
          </table>

        </div>
        
          </div >
          <img class="loading-gif-mobile" v-if="loading" src="https://assets.materialup.com/uploads/b68f4460-aaa9-4e19-99d8-232dfea1c537/preview.gif">
          <div class="mobile-box-score" v-bind:class="{hidden: isDesktop}" v-else> 
          <div class="team-names-container"> 
            <div class="team-logo"> 
              <h1 class="team-names" v-bind:class="{grayed: showStats}" v-on:click="showStats = false">{{ boxScore.boxscore.teams[1].team.name }}</h1>
              <img alt="team-logo" :src="boxScore.boxscore.teams[1].team.logo"/>
            </div>
  
            <div class="team-logo"> 
              <h1 class="team-names" v-bind:class="{grayed: !showStats}" v-on:click="showStats = true">{{ boxScore.boxscore.teams[0].team.name }}</h1>
              <img alt="team-logo" :src="boxScore.boxscore.teams[0].team.logo"/>
            </div>
            <div class="stat-dropdown" style="display: flex"> 
            <ul> 
              <h4 v-on:click="handleDropdown()" style="border: 1px solid black; width: fit-content; margin: 0px; padding: 10px;">Pick a Stat To View <i class="fa fa-sharp fa-solid fa-sort-down"></i></h4> 
              <li v-show="showDropdown" class="stats-list" v-for="(label, index) in boxScore.boxscore.players[0].statistics[0].labels" :key="index" v-on:click="statToShow = index; handleDropdown() "> 
                {{ label }}
              </li>
            </ul>
          </div>
          </div>

          <table v-show="showStats">
             <tr>
              <th></th>
              <th v-for="(label, index) in selectedStats.labels[statToShow]" :key="index" v-on:click="statToShow = index" v-show="index === 0">
                {{ selectedStats.labels[statToShow] }} 
              </th>
            </tr>
            <tr v-for="(athlete, index) in boxScore.boxscore.players[0].statistics[0].athletes" :key="index">
              <th>
                {{ athlete.athlete.displayName }}
              </th>
              <th> 
                {{ athlete.stats[statToShow]  }} 
              </th>
            </tr>
          </table>


          <table v-show="!showStats">
             <tr>
              <th></th>
            
              <th v-for="(label, index) in selectedStats.labels[statToShow]" :key="index" v-on:click="statToShow = index" v-show="index === 0">
                {{ selectedStats.labels[statToShow] }} 
              </th>
            </tr>
            <tr v-for="(athlete, index) in boxScore.boxscore.players[1].statistics[0].athletes" :key="index">
              <th>
                {{ athlete.athlete.displayName }}
              </th>
              <th> 
                {{ athlete.stats[statToShow]  }} 
              </th>
            </tr>
          </table>

        </div>
          
          <!-- bottom nav bar in mobile mode -->
          <BottomNavigation v-bind:class="{hidden: isDesktop}"></BottomNavigation>
    </div>

      </template>
      
      <script>
      
      import navBar from '../components/Navbar.vue';
      import MobileNavbar from '../components/MobileNavbar.vue'
      import BottomNavigation from './BottomNavigation.vue';
      import LiveScores from '@/services/LiveScores';
      import ArticleService from '@/services/ArticleService';
      import ArticleDataService from '@/services/ArticleDataService';
      import LoggerService from '@/services/LoggerService';


      export default {
        name: "Box-Score",
        props: {
        eventId: {
        required: true
        }
        },
        components: {
          navBar,
          MobileNavbar,
          BottomNavigation
        },
        data() {
          return {
            loading: true,
            //navbar toggle data property
            isDesktop: false,
            boxScore: [],
            trendingArticles: [],
            userLikes: [],
            showStats: false,
            statToShow: 0,
            statsTeam1: [],
            showListStat: false,
            showDropdown: false
          }
        },
        async created(){
            LiveScores.listNbaBoxScore(this.$route.params.eventId).then(response => {
                this.boxScore = response.data
                this.loading = false
     
            }),
            ArticleService.listTrendingArticles().then(response =>{
                this.trendingArticles = response.data
            }),
            ArticleDataService.checkPostLike().then(response => {
                this.userLikes = response.data
            })
        },
        mounted() {
          //depending on size renders the correct navbar
          this.isDesktop = window.matchMedia("(min-width: 913px)").matches
          window.addEventListener("resize", this.handleResize)
          window.addEventListener('scroll', this.handleScroll)
        
        },
      computed: {
        articleIsLiked() {
    return articleId => this.userLikes.some(like => like.id === articleId || like.article_id === articleId);
  },
  selectedStats() {
      const selectedStat = this.boxScore.boxscore.players[0].statistics.find(stat => stat.name === this.selectedOption);
      console.log("seleected stat: " + JSON.stringify(selectedStat.labels[0]))
      const athletes = this.boxScore.boxscore.players[0].statistics[0].athletes[0].athlete.displayName;
      const labels = selectedStat.labels;
      console.log("seleected label: " + JSON.stringify(this.boxScore.boxscore.players[1].statistics[0].athletes[0].stats))
      return { athletes, labels };
    },

    },
        methods: {
          arrayMakerForStats(stats){
            return this.statsTeam1 = stats.split(', ')
          },
          //handles resize to toggle navbars
          handleResize() {
            this.isDesktop = window.matchMedia("(min-width: 913px)").matches
            this.newsOrTrendingClicked = true
          },
          handleLikeClick(){
        if(this.userLikes.some(like => like.id === this.postLiked)){
          ArticleDataService.unlikeAPost(this.postLiked).then(response=> {
            if(response.status === 200 ){
              this.userLikes = this.userLikes.filter(like => like.id !== this.postLiked);
            
              this.isLiked = false;
            }
          })
        } else {
          let like = {
            "liked": true
          };
          if(this.userDislikes.some(like => like.id === this.postLiked)){
            ArticleDataService.unlikeAPost(this.postLiked).then(response=> {
            if(response.status === 200 ){
              this.userDislikes = this.userDislikes.filter(like => like.id !== this.postLiked);
             
              this.isLiked = false;
            }
          })
          }
          if(this.usersSavedForLater.some(saved => saved.id === this.postLiked)){
            ArticleDataService.removeSaveForLater(this.postLiked).then(response=> {
            if(response.status === 200 ){
              this.usersSavedForLater = this.usersSavedForLater.filter(saved => saved.id !== this.postLiked);
           
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

    handleDropdown(){
      if(this.showDropdown == false){
        this.showDropdown = true
      } else {
        this.showDropdown = false
      }
    }
          
      },
      //checks what size the window is on load
        beforeUnmount() {
          window.removeEventListener("resize", this.handleResize)
        }
      }
      
      
      
      </script>
      
      <style scoped>

      img{
        height: 40px;
      }

      .team-names-container {
        width: max-content;
        margin-top: 10em;
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
      }

      @media (max-width: 916px){
        .team-names-container {
        margin-top: 15em;

      }
    }

      @media (max-width: 740px){
        .team-names-container {
        margin-top: 10em;
      }

      }
      .team-names{
        margin: 30px;
        cursor: pointer;
      }

      .team-logo{
        display: flex;
        align-items: center;
      }

table{
    width: 90vw;
    height: 60vh;
    margin-top: 40px;
}

tr:nth-child(even) {
  background-color: #ffffff;
}
tr:nth-child(odd) {
  background-color: #e0edff;
}

      a{
        text-decoration: none;
        color: black;
        transition: all 0.3s ease;
        font-family: 'Titillium Web', sans-serif;
      }
      a:hover{
        color: #3088d4;
      }
  

      .container{
        display: flex;
        flex-direction: row;
        background-color: #eeeeee;
        height: 100vh;
      }

      .grayed{
        color: #9e9e9e;
      }
  
      .a-article{
        text-decoration: none;
        color: #dd3434;
        transition: all 0.3s ease;
        font-family: 'Titillium Web', sans-serif;
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

  
    
      button{
        background: none;
        border: none;
        margin:5px;
        cursor: pointer;
      }
      
      .heading-holder{
          margin-top: 150px;
          margin-left: 16%;
          font-family: 'Titillium Web', sans-serif;
          text-align: left
          ;
      }
    
    
      /* TRENDING */
    
    #trending{
      margin-top: 7%;
        flex-basis: 17%;
        align-items: center;
        border: 2px solid #c3c5cc;
        font-weight: 900;
        background-color: #fff;
    }

    @media (max-width: 1000px) {
      #trending{
      margin-top: 13%;
      }
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

      
      
      .stats-list{
        width: 100%;
        list-style-type: none;
        border: 0.5px solid gainsboro;
        z-index: 1;
        background-color: white;
        cursor: pointer;
        transition: all 0.3s ease;
        animation: fadeInTop 0.3s ease-in;
      }
      @keyframes fadeInTop {
    from {
        transform: translateY(0%);
        opacity: 0;
    }
    to {
        transform: translateY(100);
        opacity: 1;
    }
  }

      .stat-dropdown{
        width: max-content;
        margin-top: 67px;
        display: flex;
        justify-content: center;
        z-index: 1;
        position: absolute;
        cursor: pointer;
      }
    
      .mobile-box-score{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;  
        width: 100%;
      }

      .loading-gif{
        height: 25%;
        width: 15%;
        margin: auto;
        }
        .loading-gif-mobile{
        height: 25%;
        width: 15%;
        margin-top: 50%;
        }

      </style>