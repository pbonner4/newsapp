<template>
    <div>
      <h1 class="comments-header">Chop it Up Below!<i class="fa-solid fa-scissors"></i></h1>
      <div class="initial-comment">
      <textarea class="initial-comment-box" v-model="initialComment" placeholder="Comment here..." v-if="comments.length !== 0"></textarea>
      <textarea class="initial-comment-box" v-model="initialComment" placeholder="Be the First To Comment!" v-if="comments.length == 0"></textarea>
      <button class="initial-comment-button" v-on:click="handleNewComment()">Post Comment</button>
    </div>
      <div v-for="(articleComment, index) in comments" v-bind:key="articleComment.article_id">
        <div class="comment" v-bind:class="{ 'reply': comments[index-1] && comments[index-1].comment_id === articleComment.comment_id }">
          <div> 
           <strong> <p>{{ articleComment.username }}</p> </strong> 
            
            <p>{{ articleComment.comment }}</p>
            <textarea v-if="selectedComment == articleComment.id" v-model="comment"></textarea>
          </div>
        
          <div class="username-date">
            <p style="font-size=16px;">{{ (articleComment.time_commented).slice(0,19).replace('T', ' ') }}</p>
            <button v-if="!showReplyBox" v-on:click="showReplyBox = true; selectedComment = articleComment.id; selectedCommentId = articleComment.comment_id">Reply</button>
            <button v-if="showReplyBox" v-on:click="showReplyBox = false; selectedComment = null; handleReply()">Post Reply</button>
          </div>
        </div>
    </div>
    </div>
  </template>
  
  <script>

  import CommentsService from '@/services/CommentsService';
  import LoggerService from '@/services/LoggerService';
  import { v4 as uuidv4 } from 'uuid';
  
  export default {
    props: ['articleId'],
    data() {
      
      return {
        comments: [],
        comment: '',
        initialComment: '',
        showReplyBox: false,
        selectedComment: null,
        selectedCommentId: null,
        articleReply : {
          "comment_id": "",
          "comment" : ""
        },
        articleComment : {
          "comment_id": "",
          "comment" : ""
        },
        newId: null,
      }
    },
    created(){
      CommentsService.getArticleComments(this.articleId).then(response => {
        this.comments = response.data
      });
    },
    methods: {
      handleReply(){
        if(this.comment != ''){
        this.logComment()
        this.articleReply.comment_id = this.selectedCommentId
        this.articleReply.comment = this.comment
        this.replyToComment()
        }
      },
      handleNewComment(){
        if(this.initialComment != ''){
        this.logComment()
        this.articleComment.comment_id = uuidv4()
        this.articleComment.comment = this.initialComment
        this.newComment()
        }
      },
      replyToComment() {
        CommentsService.postComment(this.articleId, this.articleReply).then(response => {
          if(response.status == 200){
            window.location.reload()
            console.log("commented")
          }
        })
      },
      newComment(){
        CommentsService.postComment(this.articleId, this.articleComment).then(response => {
          if(response.status == 200){
            window.location.reload()
            console.log("commented")
          }
        })
      },
      logComment(){
            //log it as an event for the recommender using the event logger
          const event = {
            user_id: this.currentUser,
            articleId: this.articleId,
            event: `commented`,
            sessionId: this.$store.state.sessionId
          }

          LoggerService.logger(event).then(response => {
            if(response.status == 200){
              console.log('comment logged')
            } else {
              console.log('not working')
            }
          })
      },
    }
  }
  </script>
  
<style scoped>

i{
  color: #dd3434;
}

button{
  margin-bottom: 10%;
  padding:5px;
  background-color: #0060b5;
  border: 1px solid black;
  border-radius: 20%;
  color:#fff;
  text-decoration:none;
  cursor: pointer;
}
.comments-header{
  text-align: left;
}

.comment {
  font-family: Arial, Helvetica, sans-serif;
  padding: 2rem;
  font-size: larger;
  display: flex;
  justify-content: space-between;
  background-color: rgb(181, 181, 181);
  border-radius: 2%;
  margin-bottom: 10px;
  width: 75%;

}

.initial-comment{
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.initial-comment-box{
  width: 45%;
  height: 100%;
  font-family: Arial, Helvetica, sans-serif;
}

.initial-comment-button{
  margin-top: 20px;
  width: 5em;
  margin-bottom: 8em;
  display: flex;
  align-items: center;
}
.username-date{
  font-size:small;
}

.reply{
  margin-left: 50px;
  background-color: lightgrey;
}

</style>
