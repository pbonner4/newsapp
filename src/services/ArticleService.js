import axios from 'axios';
const currentToken = localStorage.getItem('token')
const apiClient =axios.create({
  baseURL: "http://localhost:9000",
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${currentToken}`
  }
});

export default {
    getAllArticles(){
        return apiClient.get('/articles')
    },
    getAllArticleContent(){
        return apiClient.get('/allArticlesContent')
    },
    getArticleContent(id){
        return apiClient.get(`/article/post/${id}`)
    },
    listTrendingArticles(){
        return apiClient.get(`/articles/trending`)
    },
    listNbaArticles(){
        return apiClient.get('/articles/nba')
    },
    listNflArticles(){
        return apiClient.get('/articles/nfl')
    },
    listNhlArticles(){
        return apiClient.get('/articles/nhl')
    },
    listMmaArticles(){
        return apiClient.get('/articles/mma')
    },
    listBusinessArticles(){
        return apiClient.get('/articles/business')
    },
    listMovieArticles(){
        return apiClient.get('/articles/entertainment/movies')
    },
    listTvArticles(){
        return apiClient.get('/articles/entertainment/television')
    },
    listMusicArticles(){
        return apiClient.get('/articles/entertainment/music')
    },
    listLeftWingArticles(){
        return apiClient.get('/articles/politics/left')
    },
    listRightWingArticles(){
        return apiClient.get('/articles/politics/right')
    },
    listCenterArticles(){
        return apiClient.get('/articles/politics/center')
    },
    listPoliticsArticles(){
        return apiClient.get('/articles/politics')
    },
    listIntheaters(){
        return apiClient.get('/nowPlaying')
    },
    listComingSoon(){
        return apiClient.get('/comingSoon')
    },
    listContentBasedArticles(){
        return apiClient.get(`/curatedContentBased`)
    },
    listSuggestedContentBasedArticles(articleId){
        return apiClient.get(`/curatedContentBasedPerArticle/${articleId}`)
    },
    listCollaborativeArticles(){
        return apiClient.get(`/matrixFactorizationArticles`)
    },
    listFwlsArticles(){
        return apiClient.get(`/fwlsArticles`)
    },
    listSearchedArticles(searchTerm){
        return apiClient.get(`/searchArticles/${searchTerm}`)
    }
}