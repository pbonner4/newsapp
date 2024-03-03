<template>
  <!-- bottom nav bar in mobile mode -->
  <nav class="trending-nav">
    <router-link v-bind:to="{ name: 'home'}" style="margin-top: 13px;" class="trending-nav-search-button" v-on:click="sectionClicked = 'home'">
      <i v-bind:class="{ red: isSectionActive('home') }" class="fa-solid fa-newspaper"></i>
    </router-link>
    <router-link v-bind:to="{ name: 'TrendingPage'}" style="margin-top: 13px;" class="trending-nav-search-button" v-on:click="sectionClicked = 'trending'">
      <i v-bind:class="{ red: isSectionActive('TrendingPage') }" class="fa-solid fa-arrow-trend-up fa-lg"></i>
    </router-link>
    <router-link v-bind:to="{ name: 'searchBar'}" style="margin-top: 13px;" class="trending-nav-search-button" v-on:click="sectionClicked = 'search'">
      <i v-bind:class="{ red: isSectionActive('searchBar') }" class="fa-solid fa-magnifying-glass fa-lg"></i>
    </router-link>
    <router-link v-bind:to="{ name: 'profilePage'}" style="margin-top: 13px;" class="trending-nav-search-button" v-on:click="sectionClicked = 'profile'">
      <i v-bind:class="{ red: isSectionActive('profilePage') }" class="fa-solid fa-user fa-lg"></i>
    </router-link>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      sectionClicked: ''
    }
  },
  methods: {
    isSectionActive(sectionName) {
      console.log("route: " + this.$route.name)
      console.log("section: " + sectionName)
      return this.$route.name === sectionName;
    }
  }
}
</script>



<style scoped>

i {
  background: none;
    border: none;
    margin: 1.1em;
    cursor: pointer;
    color: #0b7ee3;
}
.red{
  color: #dd3434;
}

a {
  text-decoration: none;
    color: black;
    transition: all 0.3s ease;
    font-family: 'Titillium Web', sans-serif;
    margin-top: 13px;
    
}
button{
  background: none;
    border: none;
    margin: 2em;
    cursor: pointer;
}

.i-changed {
    color: #dd3434;
}

.hidden{
    display: none;
}

.trending-nav{
    display: flex;
    justify-content: space-around;
    border-top: 2px solid #0b7ee3;
    border-radius: 4%;
    background-color: #fff;
    z-index: 1;
    position: fixed;
    bottom: 0;
    width: 100%;
    padding: .1em;
}

@media (min-width: 100px) and (max-width: 913px) {
    .trending-nav {
        background-color: #fff;
        z-index: 1;
        position: fixed;
        bottom: 0;
        width: 100%;
}
}

</style>
