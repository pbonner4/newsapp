<template>
<div>
    <div>
          <navBar v-bind:class="{hidden: !isDesktop}" v-on:page-selected="onPageSelected"/>
          <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
      </div>
    <businessPage v-show="currentPage == 'business'"></businessPage>
    <centerPage v-show="currentPage =='center'"></centerPage>
    <homePage v-show="currentPage =='home'"></homePage>
    <inTheatersPage v-show="currentPage =='inTheaters'"></inTheatersPage>
    <leftWingPage v-show="currentPage =='leftWing'"></leftWingPage>
    <mmaPage v-show="currentPage =='mma'"></mmaPage>
    <moviePage v-show="currentPage =='movie'"></moviePage>
    <musicPage v-show="currentPage =='music'"></musicPage>
    <nbaPage v-show="currentPage =='nba'"></nbaPage>
    <nflPage v-show="currentPage =='nfl'"></nflPage>
    <nhlPage v-show="currentPage =='nhl'"></nhlPage>
    <rightWingPage v-show="currentPage =='right'"></rightWingPage>
    <televisionPage v-show="currentPage =='television'"></televisionPage>
    <profilePage v-show="currentPage =='profile'"></profilePage>
    <Login v-show="currentPage == 'login'"></Login>
    <Register v-show="currentPage == 'register'"></Register>

</div>
</template>

<script>

import NavBar from '../components/SpaViews/Navbar.vue'
import MobileNavbar from '../components/SpaViews/MobileNavbar.vue'
import businessPage from '../components/SpaViews/Business.vue' 
import centerPage from '../components/SpaViews/Center.vue'
import homePage from '../components/SpaViews/Home.vue'
import inTheatersPage from '../components/SpaViews/InTheaters.vue'
import leftWingPage from '../components/SpaViews/LeftWing.vue'
import mmaPage from '../components/SpaViews/Mma.vue'
import moviePage from '../components/SpaViews/Movie.vue'
import televisionPage from '../components/SpaViews/Movie.vue'
import musicPage from '../components/SpaViews/Music.vue'
import nbaPage from '../components/SpaViews/Nba.vue'
import nflPage from '../components/SpaViews/Nfl.vue'
import nhlPage from '../components/SpaViews/Nhl.vue'
import rightWingPage from '../components/SpaViews/RightWing.vue'
import profilePage from '../components/SpaViews/Profile.vue'
import Login from './Login.vue'
import Register from './Register.vue'


export default {
    name:"spaTest",
    components: {
        Login,
        Register,
        MobileNavbar,
        NavBar,
        businessPage,
        centerPage,
        homePage,
        inTheatersPage,
        leftWingPage,
        mmaPage,
        moviePage,
        musicPage,
        nbaPage,
        nflPage,
        nhlPage,
        rightWingPage,
        televisionPage,
        profilePage
    },

    data() {
    return {
        //navbar toggle data property
        isDesktop: false,
        currentPage: 'home'
    }
},
    mounted() {
      //depending on size renders the correct navbar
      this.isDesktop = window.matchMedia("(min-width: 913px)").matches
    },
    //checks what size the window is on load
    beforeUnmount() {
      window.removeEventListener("resize", this.handleResize)
    },
    methods: {
      //handles resize to toggle navbars
      handleResize() {
        this.isDesktop = window.matchMedia("(min-width: 913px)").matches
        this.newsOrTrendingClicked = true
      },
      onPageSelected(page){
        this.currentPage = page
      }
    }
}
</script>

<style scoped>

.hidden {
      display: none;
  }

</style>