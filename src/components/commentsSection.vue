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

  .fa-scissors {
    color: #dd3434;
  }
  
  .comments-header {
    text-align: center;
    font-size: 24px;
    padding: 20px 0;
  }
  
  .comment {
    font-family: Arial, Helvetica, sans-serif;
    padding: 15px;
    font-size: 16px;
    background-color: #f9f9f9;
    border-radius: 10px;
    margin-bottom: 10px;
    display: flex;
    flex-direction: column;
    border: 1px solid #ccc;
  }
  
  .initial-comment-box {
    width: 100%;
    height: 100px;
    font-family: Arial, Helvetica, sans-serif;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    resize: vertical;
  }
  
  button {
    width: 150px;
    height: fit-content;
    padding: 10px;
    background-color: #0060b5;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin: 10px 0;
  }
  
  .username-date {
    font-size: 12px;
    color: #888;
    display: flex;
    justify-content: space-between;
  }
  
  .reply {
    margin-left: 15px;
    background-color: #f2f2f2;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
  }
  
  .cursor-blink {
    display: inline-block;
    width: 8px;
    height: 2px;
    background-color: #333;
    margin-left: 5px;
    animation: blink-animation 1s steps(5, start) infinite;
  }
  
  @keyframes blink-animation {
    to {
      visibility: hidden;
    }
  }
  
  @media (max-width: 768px) {
    .comment {
      padding: 10px;
      font-size: 14px;
    }
    
    .initial-comment-box {
      height: 80px;
      padding: 8px;
    }
    
    .initial-comment-button {
      width: 120px;
      padding: 8px;
      margin: 5px 0;
    }
    
    .username-date {
      font-size: 10px;
    }
  }
  
  </style>
