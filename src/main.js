import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from './store/index'
import axios from 'axios'
import VueLazyLoad from 'vue-lazyload'
import VueSocialSharing from 'vue-social-sharing'

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;


const app = createApp(App);
app.use(
  VueLazyLoad, {
    preLoad: 1.3,
    error: 'dist/error.png',
    loading: './loading.gif',
    attempt: 1
  });
  app.use(VueSocialSharing)
app.use(router);
app.use(store)
app.mount("#app");