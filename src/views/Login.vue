<template>
    <div class = "main-div-login">
      <div>
            <Navbar v-bind:class="{hidden: !isDesktop}"/>
            <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
      </div>
      <div class="form-and-logo">
      <div class = "tagline">
        <h1>The Conversation Starts Here.</h1>
      </div>
      <img class="logo" src="../images/coloredfinallogochanged.png" alt="logo" preload="auto"/>
      <div id="login" class="text-center">
        <h1 class = "welcome-back" >Welcome Back!</h1>
        <form class="form-signin" @submit.prevent="login">
          <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
          <div
            class="console.log console.log-danger"
            role="console.log"
            v-if="invalidCredentials"
          >Invalid username and password!</div>
          <div
            class="console.log console.log-success"
            role="console.log"
            v-if="this.$route.query.registration"
          >Thank you for registering, please sign in.</div>
          <label for="username" class="sr-only">Username</label>
          <input
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
          <label for="password" class="sr-only">Password</label>
          <input
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            v-model="user.password"
            required
          />
          <router-link v-bind:to="{ name: 'register' }">Need an account?</router-link>
          <button type="submit" >Sign in</button>
        </form>
      </div>
    </div>
    </div>
    </template>
    
    <script>
import AuthService from "../services/AuthService";
import MobileNavbar from '@/components/MobileNavbar.vue';
import Navbar from '@/components/Navbar.vue';

export default {
  name: "loginPage",
  components: {
    MobileNavbar,
    Navbar
  },
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false,
      isDesktop: false,
    };
  },
  mounted(){
        //depending on size renders the correct navbar
        this.isDesktop = window.matchMedia("(min-width: 913px)").matches
        window.addEventListener("resize", this.handleResize)
      },
  methods: {
    login() {
      AuthService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            const sessionId = this.generateSessionId();
            this.$store.commit('setSessionId', sessionId);
            this.$router.push("/");

          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
      //handles resize to toggle navbars
      handleResize() {
        this.isDesktop = window.matchMedia("(min-width: 913px)").matches
      },
    generateSessionId() {
      return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    }
  },
  beforeMount(){
        window.removeEventListener("resize", this.handleResize)
  }
};
</script>

<style scoped>


h1 {
font-family: 'proxima-nova', sans-serif;
-webkit-text-stroke: .3px #fff;
font-size: 4vh;
}


.hidden {
      display: none;
  }

.main-div-login{
  height: 100vh;
  margin: 0px;
}

#login{
display: flex;
flex-direction: column;
align-items: center;
background-color: #2179c7;
height: max-content;
width: 30vh;
padding: 2%;
border-radius: 3%;
color: #fff;
border: 2px solid #178aef;
margin-bottom: 5%;
}
.form-and-logo{
z-index: 0;
display: flex;
flex-wrap: wrap;
justify-content: center;
align-items: center;
position: relative;
background: rgb(0,146,237);
background: linear-gradient(90deg, rgba(222, 222, 222, 0.691) 2%, rgba(0,146,237,1) 100%);
height: 100%;
}
.form-control{
margin: 2%;
border-radius: 2%;
height: 7%;
width: 80%;
}

.btn{
margin-top: 7%;
background-color: #fff;
color: #178aef;
font-family: Arial, Helvetica, sans-serif;
font-weight: 800;
font-size: large;
}

.logo {
width: 40%;
height: 70%;
}

@media (max-width: 916px) {
  .logo {
width: 35%;
height: 30%;
}
}




</style>