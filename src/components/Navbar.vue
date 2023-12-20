<template>
    <nav class = "nav-container">
        <ul class="nav-main" >
            <router-link v-bind:to="{ name: 'home'}"><img src="../images/coloredfinallogochanged.png" alt="logo" preload="auto"/></router-link>
            <a  class = "nav-elements" target="__blank" href="https://barbershoptalk.shop" ><li>Barbershop Talk Store</li></a>
            <router-link class = "nav-elements" v-bind:to="{ name: 'business'}"><li v-on:click="logBusinessClick">Business</li></router-link>
            <li class="nav-elements" v-on:mouseover="toggle" v-on:mouseleave="showDropdown = false">Sports<i class="fa fa-light fa-caret-down"></i>
        <div class="dropdown-menu" v-show="showDropdown">
          <router-link class="dropdown-elements-sports" v-show="showDropdown" v-bind:to="{ name: 'nba'}"><li style="padding: 0;" v-on:click="logNbaClick">NBA </li> </router-link>
          <router-link class="dropdown-elements-sports" v-show="showDropdown" v-bind:to="{ name: 'nfl'}"><li style="padding: 0;" v-on:click="logNflClick">NFL  </li></router-link>
          <router-link class="dropdown-elements-sports" v-show="showDropdown" v-bind:to="{ name: 'nhl'}"><li style="padding: 0;" v-on:click="logNhlClick">NHL  </li></router-link>
          <router-link class="dropdown-elements-sports" v-show="showDropdown" v-bind:to="{ name: 'mma'}"><li style="padding: 0;" v-on:click="logMmaClick">MMA </li></router-link>
        </div>
      </li>
                <li class="nav-elements" v-on:mouseover="toggle" v-on:mouseleave="showDropdown = false">Politics<i class="fa fa-light fa-caret-down"></i>
        <div class="dropdown-menu" v-show="showDropdown">
          <router-link class="dropdown-elements-entertainment" v-show="showDropdown" v-bind:to="{ name: 'right-wing'}"><li style="padding: 0;" v-on:click="logRightClick">Right Wing </li> </router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showDropdown" v-bind:to="{ name: 'left-wing'}"><li style="padding: 0;" v-on:click="logLeftClick">Left Wing  </li></router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showDropdown" v-bind:to="{ name: 'center'}"><li style="padding: 0;" v-on:click="logCenterClick">Center  </li></router-link>
          
        </div>
                </li>
                <li class="nav-elements" v-on:mouseover="toggle" v-on:mouseleave="showDropdown = false">Pop Culture<i class="fa fa-light fa-caret-down"></i>
                <div class="dropdown-menu" v-show="showDropdown">
          <router-link class="dropdown-elements-entertainment" v-show="showDropdown" v-bind:to="{ name: 'inTheaters'}"><li style="padding: 0;" v-on:click="logMovieClick"> In Theaters </li> </router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showDropdown" v-bind:to="{ name: 'movies'}"><li style="padding: 0;" v-on:click="logMovieClick">Movies </li> </router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showDropdown" v-bind:to="{ name: 'television'}"><li style="padding: 0;" v-on:click="logTvClick">Television  </li></router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showDropdown" v-bind:to="{ name: 'music'}"><li style="padding: 0;" v-on:click="logMusicClick">Music  </li></router-link>
          
        </div>
                </li>
                <router-link class="nav-elements" v-bind:to="{ name: 'theCommunity'}"><li style="padding: 0;">The Barbershop </li></router-link>
                <router-link class="nav-elements" v-show="!token" v-bind:to="{ name: 'login'}"><li style="padding: 0;">Login </li></router-link>
                <router-link class="nav-elements" v-show="!token" v-bind:to="{ name: 'register'}"><li style="padding: 0;">Register </li></router-link>
                <router-link class="nav-elements" v-show="token" v-bind:to="{ name: 'profilePage'}"><li style="padding: 0;">Profile </li></router-link>
                <router-link class="nav-elements" v-show="token" v-bind:to="{ name: 'logout' }">Logout</router-link>
                <NotificationBell class="nav-bell"></NotificationBell>
        </ul>
    </nav>
</template>

<script>

import LoggerService from '@/services/LoggerService';
import ArticleDataService from '@/services/ArticleDataService';
import NotificationBell from '@/components/NotificationBell.vue';

export default {
    name: "navBar",
    components: {
      NotificationBell
    },
    data(){
        return {
            showDropdown: true,
            currentUser: 0
        }
    },
    mounted(){
        //calls for current user
        ArticleDataService.getUser().then(response => {
          this.currentUser = response.data
    })
    },
    methods:{
        toggle(){
            this.showDropdown = true;
        },
        logBusinessClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:business`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('business logged')
            } else {
              console.log('not working')
            }
          })
      },
      logNbaClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:nba`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('nba logged')
            } else {
              console.log('not working')
            }
          })
      },
      logNflClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:nfl`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('nfl logged')
            } else {
              console.log('not working')
            }
          })
      },
      logNhlClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:nhl`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('nhl logged')
            } else {
              console.log('not working')
            }
          })
      },
      logMmaClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:mma`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('mma logged')
            } else {
              console.log('not working')
            }
          })
      },
      logPoliticsClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:politics`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('pol logged')
            } else {
              console.log('not working')
            }
          })
      },
      logRightClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:right_wing`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('right logged')
            } else {
              console.log('not working')
            }
          })
      },
      logLeftClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:left_wing`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('left logged')
            } else {
              console.log('not working')
            }
          })
      },
      logCenterClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:left_wing`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('Center logged')
            } else {
              console.log('not working')
            }
          })
      },
      logMovieClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:movies`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('movie logged')
            } else {
              console.log('not working')
            }
          })
      },
      logMusicClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:music`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('music logged')
            } else {
              console.log('not working')
            }
          })
      },
      logTvClick(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: 0,
            event: `category:television`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){

              console.log('tv logged')
            } else {
              console.log('not working')
            }
          })
      },
        },
        computed: {
          token() {
            return this.$store.state.token;
          }
        }
    }
</script>

<style scoped>
/* HTML TAG ELEMENTS */

img {
    height: 250px;
    width: 250px;
    margin-bottom: 2px;
}

i {
    margin-left: 10px
}

li {
    list-style: none;
    
}

router-link {
    text-decoration: none;
}
/* NAV BAR */

.hamburger-button{
    display: none;
}
.nav-container{
    background: #3b9aed;
    display: flex;
    justify-content: flex-start;
    width: 100vw;
    height: 8rem;
    border-bottom: 2px solid #dd3434;
    z-index: 1;
    position: fixed;
    transition: all 0.5s ease;
}
.nav-main{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 6.3rem;
    margin-left: 0px;
    padding: 0%;
    z-index: 1;  
}
.nav-elements{
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    text-decoration: none;
    padding-top: 50px;
    padding-left: 20px;
    padding-right: 20px;
    color: white;
    height: 80%;
    font-family: 'Titillium Web', sans-serif;
    font-weight: 900;
    z-index: 1;
    -webkit-transition: background-color .5s;
    -moz-transition:    background-color .5s;
    -ms-transition:     background-color .5s;
    -o-transition:      background-color .5s;
    transition:         background-color .5s;
}
.nav-bell{
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    text-decoration: none;
    padding-top: 50px;
    padding-left: 20px;
    padding-right: 20px;
    color: white;
    height: 80%;
    font-weight: 900;
    z-index: 1;
    cursor: pointer;
}
.nav-bell:hover{
  color: #dd3434;
}


.nav-elements:hover{
    background-color: #dd3434;
}

.dropdown-menu{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    text-decoration: none;
    z-index: 100;
    position: absolute;
    top: 100%;
    text-align: left;
    opacity: 0;
    visibility: hidden;
    transition: all 0.5s ease-in-out;
    height: max-content;
}

.dropdown-elements-sports{
    margin-top: 0px;
    padding: 10px;
    text-align: center;
    text-decoration: none;
    z-index: 100;
    position: relative;
    width: 90px;
    height: 18px;
    color: white;
    border-top: 0.5px solid white;
    background-color: #dd3434;
    transition: all 0.5s ease-in-out;
    font-size: 22px;
}

.dropdown-elements-entertainment{
    margin-top: 0px;
    padding: 10px;
    text-align: center;
    text-decoration: none;
    z-index: 100;
    position: relative;
    width: 148px;
    height: 18px;
    color: white;
    border-top: 0.5px solid white;
    background-color: #dd3434;
    transition: all 0.5s ease-in-out;
    font-size: 22px;
}

.dropdown-elements-sports:hover{
    color:rgb(142, 141, 141);
}
.dropdown-elements-entertainment:hover{
    color:rgb(142, 141, 141);
}

.nav-elements:hover .dropdown-menu {

    transition: all 0.5s ease-in-out;
    opacity: 1;
    visibility: visible;
}



</style>