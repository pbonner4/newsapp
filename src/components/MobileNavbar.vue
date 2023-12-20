<template>
<div class = "main-div">
    <nav class = "nav-container">
        <ul class="nav-logo" >
            <router-link v-bind:to="{ name: 'home'}"><img src="@/images/coloredfinallogochanged.png" alt="logo" preload="auto" v-bind:class="{'img': showMenu}"/></router-link>
        </ul>
        <button class="hamburger-button" v-on:click="showMenu = !showMenu" v-bind:class="{'hamburger-button-clicked': !showMenu}">
        <i class="fa fa-bars"></i>
         </button>
    </nav>
    <div class="menu-items" v-bind:style="{transform: showMenu ? 'translateY(0)' : 'translateY(-100%)'}">
            <a  class = "nav-elements" target="__blank" href="https://barbershoptalk.shop" ><li>Barbershop Talk Store</li></a>
            <router-link class="nav-elements" v-bind:to="{ name: 'theCommunity'}"><li style="padding: 0;">The Barbershop </li></router-link>
                <router-link v-show="currentUser == 0" class="nav-elements" v-bind:to="{ name: 'login'}"><li style="padding: 0;">Login </li></router-link>
                <router-link v-show="currentUser == 0" class="nav-elements" v-bind:to="{ name: 'register'}"><li style="padding: 0;">Register </li></router-link>
            <router-link class = "nav-elements" v-bind:to="{ name: 'business'}"><li v-on:click="logBusinessClick">Business</li></router-link>
            <li class="nav-elements" v-on:click="toggleSports" >Sports<i class="fa fa-light fa-caret-down"></i>
                <div class="dropdown-menu" v-show="showSportDropdown">
                    <router-link class="dropdown-elements-sports" v-show="showSportDropdown" v-bind:to="{ name: 'nba'}"><li style="padding: 0;" v-on:click="logNbaClick">NBA </li> </router-link>
                    <router-link class="dropdown-elements-sports" v-show="showSportDropdown" v-bind:to="{ name: 'nfl'}"><li style="padding: 0;" v-on:click="logNflClick">NFL  </li></router-link>
                    <router-link class="dropdown-elements-sports" v-show="showSportDropdown" v-bind:to="{ name: 'nhl'}"><li style="padding: 0;" v-on:click="logNhlClick">NHL  </li></router-link>
                    <router-link class="dropdown-elements-sports" v-show="showSportDropdown" v-bind:to="{ name: 'mma'}"><li style="padding: 0;" v-on:click="logMmaClick">MMA </li></router-link>
                </div>
            </li>
            <li class="nav-elements" v-on:click="togglePolitics" >Politics<i class="fa fa-light fa-caret-down"></i>
                <div class="dropdown-menu" v-show="showPoliticsDropdown">
          <router-link class="dropdown-elements-politics" v-show="showPoliticsDropdown" v-bind:to="{ name: 'right-wing'}"><li style="padding: 0;" v-on:click="logRightClick">Right Wing </li> </router-link>
          <router-link class="dropdown-elements-politics" v-show="showPoliticsDropdown" v-bind:to="{ name: 'left-wing'}"><li style="padding: 0;" v-on:click="logLeftClick">Left Wing  </li></router-link>
          <router-link class="dropdown-elements-politics" v-show="showPoliticsDropdown" v-bind:to="{ name: 'center'}"><li style="padding: 0;" v-on:click="logCenterClick">Center  </li></router-link>
          
        </div>
                </li>
                <li class="nav-elements" v-on:click="toggleEntertainment" >Entertainment<i class="fa fa-light fa-caret-down"></i>
                <div class="dropdown-menu" v-show="showEntertainmentDropdown">
          <router-link class="dropdown-elements-entertainment" v-show="showEntertainmentDropdown" v-bind:to="{ name: 'inTheaters'}"><li style="padding: 0;" v-on:click="logMovieClick"> In Theaters </li> </router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showEntertainmentDropdown" v-bind:to="{ name: 'movies'}"><li style="padding: 0;" v-on:click="logMovieClick">Movies </li> </router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showEntertainmentDropdown" v-bind:to="{ name: 'television'}"><li style="padding: 0;" v-on:click="logTvClick">Television  </li></router-link>
          <router-link class="dropdown-elements-entertainment" v-show="showEntertainmentDropdown" v-bind:to="{ name: 'music'}"><li style="padding: 0;" v-on:click="logMusicClick">Music  </li></router-link>
          
        </div>
                </li>
                <router-link class="nav-elements" v-show="showDropdown" v-bind:to="{ name: 'login'}"><li style="padding: 0;">Login </li></router-link>
                <router-link class="nav-elements" v-show="token" v-bind:to="{ name: 'profilePage'}"><li style="padding: 0;">Profile </li></router-link>
                <router-link class="nav-elements" v-bind:to="{ name: 'logout' }" v-show="token">Logout</router-link>
            </div>
    
</div>
</template>

<script>

import LoggerService from '@/services/LoggerService';
import ArticleDataService from '@/services/ArticleDataService';

export default {

    name: "MobileNavbar",
    components: {
    },
    data(){
        return {
            currentUser: 0,
            showSportDropdown: false,
            showEntertainmentDropdown: false,
            showPoliticsDropdown: false,
            showMenu: false,
        }
    },
    created(){
        //calls for current user
        ArticleDataService.getUser().then(response => {
          this.currentUser = response.data
    })
    },
    methods:{
        toggleSports(){
            if(this.showSportDropdown == true){
            this.showSportDropdown = false;
            } else {
                this.showSportDropdown = true;
            }
        },
        toggleEntertainment(){
            if(this.showEntertainmentDropdown == true){
            this.showEntertainmentDropdown = false;
            } else {
                this.showEntertainmentDropdown = true;
            }
        },
        togglePolitics(){
            if(this.showPoliticsDropdown == true){
            this.showPoliticsDropdown = false;
            } else {
                this.showPoliticsDropdown = true;
            }
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
    height: 100%;
    width: 27%;
    margin-bottom: 0px;
}


li {
    list-style: none;
}

.nav-bell{
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    text-decoration: none;
    padding-top: 50px;
    padding-left: 20px;
    padding-right: 14px;
    color: white;
    height: 80%;
    font-weight: 900;
    z-index: 3;
    cursor: pointer;
}

router-link {
    text-decoration: none;
}
/* NAV BAR */

.nav-logo{
    padding: 0px;
    margin: 0px;
}
.main-div{
    display: flex;
    flex-direction: column;
    width: 100%;
}

.menu-items{
    position: fixed;
    top: 18vh;
    width: 100%;
    padding: 10px;
    background-color: #dd3434;
    transform: translateY(-100%);
    transition: transform 0.8s ease-out;
}
.burger-bell{
  display: flex;
  flex-direction: column;
}
/* mobile responsiveness */
@media (max-width: 208px) {
  .menu-items{
    top: 9vh;
  }
}

@media (min-width: 209px) and (max-width: 313px) {
  .menu-items{
    top: 14vh;
  }
}

@media (min-width: 314px) and (max-width: 358px) {
  .menu-items{
    top: 15vh;
  }
}

@media (min-width: 375px) and (max-width: 376px) {
  .menu-items{
    top: 15.5vh;
  }
}
@media (min-width: 413px) and (max-width: 414px) {
  .menu-items{
    top: 13vh;
  }
}

@media (min-width: 389px) and (max-width: 390px) {
    .menu-items{
    top: 13.2vh;
    }
}

@media (min-width: 361px) and (max-width: 374px) {
    .menu-items{
    top: 14.5vh;
    }
}

@media (min-width: 377px) and (max-width: 388px) {
    .menu-items{
    top: 14.5vh;
    }
}

@media (min-width: 391px) and (max-width: 410px) {
    .menu-items{
    top: 13vh;
    }
}

@media (min-width: 359px) and (max-width: 360px) {
    .menu-items{
    top: 14.3vh;
    }
}

@media (min-width: 411px) and (max-width: 412px) {
    .menu-items{
    top: 12vh;
    }
}

@media (min-width: 767px) and (max-width: 768px) {
    .menu-items{
    top: 19.8vh;
    }
}

@media (min-width: 911px) and (max-width: 912px) {
    .menu-items{
    top: 17vh;
    }
}

@media (min-width: 539px) and (max-width: 540px) {
    .menu-items{
    top: 18vh;
    }
}








.hamburger-button{
    display: none;
}
.nav-container{
    background: #3b9aed;
    display: flex;
    justify-content: flex-start;
    width: 100%;
    border-bottom: 2px solid black;
    z-index: 3;
    position: fixed;
    transition: all 0.5s ease;
}
.nav-main{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 10vh;
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
    flex-direction: column;
    padding-top: 27px;
    padding-left: 20px;
    padding-right: 20px;
    color: white;
    border-top: .5px solid white;
    height: 80%;
    font-family: 'Titillium Web', sans-serif;
    font-size: large;
    font-weight: 900;
    z-index: 3;
    -webkit-transition: background-color .5s;
    -moz-transition:    background-color .5s;
    -ms-transition:     background-color .5s;
    -o-transition:      background-color .5s;
    transition:         background-color .5s;
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
    position: relative;
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
    height: 50%;
    color: white;
    border-top: 0.5px solid white;
    background-color: #dd3434;
    transition: all 0.5s ease-in-out;
    font-size: 18px;
}

.dropdown-elements-entertainment{
    margin-top: 0px;
    padding: 10px;
    text-align: center;
    text-decoration: none;
    z-index: 100;
    position: relative;
    width: 90px;
    height: 50%;
    color: white;
    border-top: 0.5px solid white;
    background-color: #dd3434;
    transition: all 0.5s ease-in-out;
    font-size: 18px;
}

.dropdown-elements-politics{
    margin-top: 0px;
    padding: 10px;
    text-align: center;
    text-decoration: none;
    z-index: 100;
    position: relative;
    width: 60px;
    height: 50%;
    color: white;
    border-top: 0.5px solid white;
    background-color: #dd3434;
    transition: all 0.5s ease-in-out;
    font-size: 18px;
}




.nav-elements:hover .dropdown-menu {
    transition: all 0.5s ease-in-out;
    opacity: 1;
    visibility: visible;
}

.hamburger-button {
    display: block;
  background: transparent;
  border: none;
  color: grey;
  cursor: pointer;
  font-size: 1.5rem;
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
}

.hamburger-button-clicked{
    color: white;
}


</style>