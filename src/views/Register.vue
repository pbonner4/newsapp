<template>
    <div class="main-div">
          <div>
            <Navbar v-bind:class="{hidden: !isDesktop}"/>
            <MobileNavbar v-bind:class="{hidden: isDesktop}"/>
          </div>
      <div class="registration-form-and-logo">
      <div class="tagline">
        <h1>The Conversation Starts Here.</h1>
      </div>
      <img class="logo" src="../images/coloredfinallogochanged.png" alt="logo" preload="auto"/>
        <form class="form-register" @submit.prevent="register">
          <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
          <button 
            class="google-signin-button" @click="redirectToGoogle">Sign up with Google
            <img src="https://icon2.cleanpng.com/20180330/jle/kisspng-google-chrome-computer-icons-chrome-os-web-browser-chrome-5abe00a5122e24.5341057015224014450745.jpg"/>
          </button>
          <p>or</p>
          <div class="console.log console.log-danger" role="console.log" v-if="registrationErrors">
            {{ registrationErrorMsg }}
          </div>
          <div class="input-fields">
          <div class="input-boxes"> 
            <label for="first-name" class="sr-only"></label>
          <input
            type="text"
            id="first-name"
            class="form-control"
            placeholder="First Name"
            v-model="first_name"
            required
            autofocus
          />
          <label for="last-name" class="sr-only"></label>
          <input
            type="text"
            id="last-name"
            class="form-control"
            placeholder="Last Name"
            v-model="last_name"
            required
            autofocus
          />
          </div>
          <div class="input-boxes"> 
            <label for="username" class="sr-only"></label>
          <input
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
          <input
            type="text"
            id="email"
            class="form-control"
            placeholder="Email"
            v-model="user.email"
            required
            autofocus
          />
          </div>
          <div class="input-boxes"> 
            <label for="password" class="sr-only"></label>
          <input
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            v-model="user.password"
            required
          />
          <input
            type="password"
            id="confirmPassword"
            class="form-control"
            placeholder="Confirm Password"
            v-model="user.confirmPassword"
            required
          />
          </div>
        </div>
          <router-link v-bind:to="{ name: 'login' }">Have an account? Login here.</router-link>
          <p style="font size: 5px">By registering for an account you will be automatically subscribed to our AI-Driven daily newspaper!</p>
          <p style="font size: 5px">You may unsubscribe at any time.</p>
          <button class="btn btn-lg btn-primary btn-block" type="submit" >
            Join Now
          </button>
        </form>
      </div>
       <section class="emoji-features" >
            <div class = "emoji-container" >
              <div class = "emoji-header-container">
                <h2 class="emoji-header">Join the Growing News Community</h2>
              </div>
              <div class = "features">
              <div class = "flex-features">
                <div class="div-for-inside-circle">
                  <i class="fa fa-solid fa-robot fa-4x"></i>
                <h3 class="single-feature">
                  Personalized News Feed
                </h3>
                </div>
              </div>
              <div class = "flex-features" >
                <div class="div-for-inside-circle">
                  <i class="fa-sharp fa-solid fa-inbox fa-4x"></i>
                <h3 class="single-feature">
                  FREE AI-Driven 
                  Daily Newspaper
                </h3>
                </div>
              </div>
              <div class = "flex-features" >
                <div class="div-for-inside-circle">
                  <i class="fa fa-solid fa-newspaper fa-4x"></i>
                <h3 class="single-feature-2">
                  Stay Up To Date with Everything
                </h3>
                </div>
              </div>
              <div class = "flex-features" >
                <div class="div-for-inside-circle">
                  <i class="fa fa-solid fa-comments fa-4x"></i>
                <h3 class="single-feature">
                  Always be Prepared To Talk About Anything
                </h3>
                </div>
              </div>
            </div>
          </div>
            
        </section>
        <section class = "big-list-of-features">
          <div class="demo-picture">
            <img class="webpage-img" src="../images/webpage.jpg" alt="webpage.png">
            </div>
            <div class="bulleted-list-div">
              <ul class="bulleted-list" >
                <h1 class = "header-list">Stay Up to Date with EVERYTHING All in One Place!</h1>
                <li class="list-li" >
                  <div class="feature-highlight">A Personalized News Feed</div>
                  <p class="list-p" >Get a personalized news feed tailored to your interests and preferences, thanks to machine learning technology that learns what you like and delivers the stories that matter most to you.</p>
                </li>
                <li class="list-li" > 
                  <div class="feature-highlight">Every Perspective</div>
                  <p class="list-p" >Get access to a diverse range of sources and perspectives, ensuring you're always up to date on the latest news and opinions.</p>
                </li>
                <li class="list-li"> 
                  <div class="feature-highlight">Chop it up</div>
                  <p class="list-p" >Share your thoughts, opinions, and ideas with a diverse group of individuals who are just as passionate as you are.</p>
                </li>
              </ul>
              </div>
        </section>
        <section class = "big-list-of-features">
          <div class="demo-picture">
            <img class="webpage-img" src="../images/webpage.jpg" alt="webpage.png">
            </div>
            <div class="bulleted-list-div">
              <ul class="bulleted-list" >
                <h1 class = "header-list">Every Subject, EVERY Perspective!</h1>
                <li class="list-li" > 
                  <div class="feature-highlight">Make New Friends, Have Your Opinions Heard</div>
                  <p class="list-p" >Post you thoughts to "The Barbershop" where people from all over the platform can like or dislike your opinion</p>
                </li>
                <li class="list-li" >
                  <div class="feature-highlight">Free Respectful Speech</div>
                  <p class="list-p" >Join a community that values diversity, inclusion, and respect, and fosters open and honest dialogue and exchange of ideas, while also having a sense of humor.</p>
                </li>
                <li class="list-li">
                  <div class="feature-highlight">An AI-Driven Newsletter curated to your interests.</div>
                  <p class="list-p" >Get a daily dose of news tailored just for you: Our AI-driven newsletter personalizes your news based on your interests, previous clicks, and reading history.</p>
                </li>
              </ul>
              </div>
        </section>
        <div class = "final-sell">
        <h1 class="ready">Ready to Sign Up and Join the growing community?</h1>
        <a id="sign-up" v-on:click="scrollToTop">
            Click to Sign Up!
          </a>
        </div>
      </div>
    </template>
    
    <script>
    import authService from '../services/AuthService';
    import MobileNavbar from '@/components/MobileNavbar.vue';
    import Navbar from '@/components/Navbar.vue';
    import axios from 'axios';
    
    export default {
      name: 'registerPage',
      components: {
        MobileNavbar,
        Navbar
    },
      data() {
        return {
          colorPreference: true,
          user: {
            username: '',
            password: '',
            email: '',
            first_name: '',
            last_name: '',
            confirmPassword: '',
            role: 'user',
          },
          registrationErrors: false,
          registrationErrorMsg: 'There were problems registering this user.',
          //navbar toggle data property
        isDesktop: false,
        };
      },
      mounted(){
        //depending on size renders the correct navbar
        this.isDesktop = window.matchMedia("(min-width: 913px)").matches
        window.addEventListener("resize", this.handleResize)
      },
      methods: {
        //login with google
      redirectToGoogle() {
          const clientId = '420442566546-saf16uv7gvrgnsfv124vdlmf1rnvhk5a.apps.googleusercontent.com';
          const redirectUri = 'http://localhost:8081';
          const scope = 'profile email';
          const responseType = 'token id_token';

          const authUrl = `https://accounts.google.com/o/oauth2/auth?client_id=${clientId}&redirect_uri=${redirectUri}&scope=${scope}&response_type=${responseType}`;
          window.location.href = authUrl;
      },
        //handles resize to toggle navbars
      handleResize() {
        this.isDesktop = window.matchMedia("(min-width: 913px)").matches
      },
        scrollToTop(){
          window.scrollTo(0,0);
        },
        register() {
          if (this.user.password != this.user.confirmPassword && this.user.email.includes('@')) {
            this.registrationErrors = true;
            this.registrationErrorMsg = 'Password & Confirm Password do not match.';
          } else {
            authService
              .register(this.user)
              .then((response) => {
                if (response.status == 201) {
                  let newEmailSubscriber = {
                    email: this.user.email,
                    first_name: this.first_name,
                    last_name: this.last_name
                  }
                  axios.post('https://public-api.rasa.io/signup/dd2a0076-639e-1bf1-5a60-50e1f2cf5642', newEmailSubscriber)
                  .then(response => {
                  // Handle the response from the API endpoint here
                      console.log(response.data);
                    })
                  .catch(error => {
                // Handle any errors that occurred during the request here
                  console.error(error);
                });
                  this.$router.push({
                    path: '/login',
                    query: { registration: 'success' },
                  });
                }
              })
              .catch((error) => {
                const response = error.response;
                this.registrationErrors = true;
                if (response.status === 400) {
                  this.registrationErrorMsg = 'Bad Request: Validation Errors';
                }
              });
          }
        },
        clearErrors() {
          this.registrationErrors = false;
          this.registrationErrorMsg = 'There were problems registering this user.';
        },

      },
      beforeMount(){
        window.removeEventListener("resize", this.handleResize)
      }
    };
    </script>

    <style scoped>

      a:hover{
      color: #fff;
    }


    h1 {
      font-family: 'proxima-nova', sans-serif;
      -webkit-text-stroke: .3px #fff;
      font-size: 4vh;
    }


  button:hover{
    cursor: pointer;
  }

    .input-fields{
      display: flex;
      flex-direction: column;
    }
    .input-boxes{
      display: flex;
    }

.main-div{
  position: relative;
z-index: 0;
widows: 100%;
}

    .hidden {
      display: none;
  }


    .form-register{
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
    }
    .registration-form-and-logo{
      z-index: -1;
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: center;
      position: relative;
      background: rgb(0,146,237);
      background: linear-gradient(90deg, rgba(222, 222, 222, 0.691) 2%, rgba(0,146,237,1) 100%);
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
    

    .features{
      margin-top: 10vh;
    }

    .emoji-container {
      height: fit-content;
    }

    .emoji-features{
      background-color: #178aef; 
      height: fit-content;
      text-align: center;
      justify-content: center;
    }

    .emoji-header{
      margin-top: 0px;
      font-size: 5vh;
      color: #fff;
    }
    .features{
      display: flex;
      align-content: center;
      flex-wrap: wrap;
      justify-content: space-evenly;
      color: #fff;
    }

    .webpage-img{
      width: 70%;
      height: 70%;
      margin-top: 4%;
      box-shadow: 0 3px 10px rgb(0 0 0 / 0.2);
    }

    .big-list-of-features{
      display: flex;
      font-size: x-large;
      font-weight: bolder;
      justify-content: space-around;
      position: relative;
      z-index: -1;
      background-color: #0480ed;
    }

    @media screen and (max-width: 916px) {
  .big-list-of-features {
    flex-wrap: wrap;
  }
.feature-highlight{
  width: 50vw;
}

.list-p{
  width: 90vw;
}
}

.list-p, .feature-highlight {
  max-width: 80%;
  overflow-wrap: break-word;
}

.header-list{
  width: 100%;
  text-align: center;
  font-size: 190%;
  margin-bottom: 20%;
}


.feature-highlight{
  background-color: #cb182a;
  width: max-content;
  text-align: center;
  padding: 5px;
  justify-content: center;
}

.bulleted-list-div{
  width: 90%;
  justify-content: center;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
}



    .big-list-of-features{
      display: flex;
      font-size: x-large;
      font-weight: bolder;
      z-index: -1;
      justify-content: space-around;
      position: relative;
      background-color: #0480ed;
    }

    .list-li{
      color: #fff;
      list-style-type: none;
      -webkit-text-stroke: .3px #cb182a;
    }

    .list-p{
      color: #fff;
      list-style-type: none;
      -webkit-text-stroke: none;

    }

    .final-sell {
      background-color: #178aef;
      height: auto;
    }
    .ready{
      margin: 0px;
    }

    #sign-up{
      font-size: xx-large;
      cursor: pointer;
    }


.google-signin-button {
  display: inline-block;
  background-color: rgb(255, 255, 255); 
  border: 1px solid #ccc; 
  border-radius: 4px; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
  color: #333; 
  font-size: 14px; 
  text-align: center;
  cursor: pointer;
  padding: 10px 16px;
  transition: background-color 0.2s, border-color 0.2s, box-shadow 0.2s;
}

.google-signin-button:hover {
  background-color: #f8f8f8; 
}

.google-signin-button img {
  width: 18px; 
  margin-right: 8px; 
  vertical-align: middle;
}


.google-signin-button:hover {
  color: #222;
}


.google-signin-button:disabled {
  pointer-events: none;
  background-color: #ccc;
  color: #666;
}


    </style>