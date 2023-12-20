import { createStore } from 'vuex'
import axios from 'axios'

const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

const store = createStore ({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    sessionId: '',
    articleContentId: 0
  },
  mutations: {
    setSessionId(state, sessionId) {
      state.sessionId = sessionId
    },
    setContentId(state, articleContentId) {
      state.articleContentId = articleContentId
    },
    SET_AUTH_TOKEN(state, token) {
      console.log('here')
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    }

  }
})

export default store