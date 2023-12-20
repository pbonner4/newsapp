import store from '../store/index'
import homePage from '../views/Home.vue'
import moviePage from '../views/Movie.vue'
import tvPage from '../views/Television.vue'
import musicPage from '../views/Music.vue'
import nbaPage from '../views/Nba.vue'
import nflPage from '../views/Nfl.vue'
import nhlPage from '../views/Nhl.vue'
import mmaPage from '../views/Mma.vue'
import loginPage from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import registerPage from '../views/Register.vue'
import businessPage from '../views/Business.vue'
import politicsPage from '../views/Politics.vue'
import rightWing from '../views/RightWing.vue'
import leftWing from '../views/LeftWing.vue'
import centerWing from '../views/Center.vue'
import inTheaters from '../views/InTheaters.vue'
import profilePage from '../views/Profile.vue'
import theCommunityPage from '../views/TheCommunity.vue'
import ArticleContent from '../views/ArticleContent.vue'
import TrendingMobile from '../views/TrendingMobile.vue'
import BoxScore from '../components/NbaBoxScore.vue'
import SearchArticlesMobile from '../components/SearchArticlesMobile'
import SearchArticlesDesktop from '../components/SearchForArticlesDesktop'
import { createRouter, createWebHistory } from 'vue-router';

    const routes = [
        {
            path: '/',
            name: 'home',
            component: homePage
        },
        {
            path: '/trending',
            name: 'TrendingPage',
            component: TrendingMobile
        },
        {
            path: '/business',
            name: 'business',
            component: businessPage
        },
        {
            path: '/theCommunity',
            name: 'theCommunity',
            component: theCommunityPage
        },
        {
            path: '/politics',
            name: 'politics',
            component: politicsPage
        },
        {
            path: '/politics/right-wing',
            name: 'right-wing',
            component: rightWing
        },
        {
            path: '/politics/left-wing',
            name: 'left-wing',
            component: leftWing
        },
        {
            path: '/politics/center',
            name: 'center',
            component: centerWing
        },
        {
            path: '/movies',
            name: 'movies',
            component: moviePage
        },
        {
            path: '/tvNews',
            name: 'television',
            component: tvPage
        },
        {
            path: '/musicNews',
            name: 'music',
            component: musicPage
        },
        {
            path: '/inTheaters',
            name: 'inTheaters',
            component: inTheaters
        },
        {
            path: '/sports/nba',
            name: 'nba',
            component: nbaPage
        },
        {
            path: '/sports/nfl',
            name: 'nfl',
            component: nflPage
        },
        {
            path: '/sports/nhl',
            name: 'nhl',
            component: nhlPage
        },
        {
            path: '/sports/mma',
            name: 'mma',
            component: mmaPage
        },
        {
            path: '/profile',
            name: 'profilePage',
            component: profilePage
        },
        {
            path: '/:category/:title/:id',
            name: 'generalArticleWithTitle',
            component: ArticleContent
        },      
        {
            path: '/:category/:categorySpecific/:id',
            name: 'moreSpecificCategoryArticle',
            component: ArticleContent
        },   
        {
            path: '/:team1/:team2/:date/boxscore/:eventId',
            name: 'Box-Score',
            props: true,
            component: BoxScore
        },
        {
            path: '/searchArticles',
            name: 'searchBar',
            component: SearchArticlesMobile
        },
        {
            path: '/searchArticles/:term',
            name: 'searchBarDesktop',
            component: SearchArticlesDesktop
        },
        {
            path: "/logout",
            name: "logout",
            component: Logout,
            meta: {
              requiresAuth: false
            }
          },    
        {
            path: '/login',
            name: 'login',
            component: loginPage,
            meta: {
                requiresAuth: false
              }
        },
        {
            path: '/register',
            name: 'register',
            component: registerPage,
            meta: {
                requiresAuth: false
              }
        }

    ]

    const router = createRouter({
        history: createWebHistory(process.env.BASE_URL),
        routes
    })

    router.beforeEach((to, from, next) => {
        // Determine if the route requires Authentication
        const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
      
        // If it does and they are not logged in, send the user to "/login"
        if (requiresAuth && store.state.token === '') {
          next("/login");
        } else {
          // Else let them go to their next destination
          next();
        }
      });

export default router;