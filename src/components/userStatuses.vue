<template>
    <div class="dashboard-container">
        <div class="status-update barber-theme">
            <textarea placeholder="What's on your mind?" class="barber-input" v-on:keydown="stopType($event)" v-model="status"></textarea>
            <div style="margin: 1em;"> 
              You Must Tag Your Thought Before Posting
            </div>
            <div> 
              <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Business'}" v-on:click="subjectTagged = 'Business'">
              Business
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Nba'}" v-on:click="subjectTagged = 'Nba'">
              Nba
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Nfl'}" v-on:click="subjectTagged = 'Nfl'">
              Nfl
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Nhl'}" v-on:click="subjectTagged = 'Nhl'">
              Nhl
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'MMA'}" v-on:click="subjectTagged = 'MMA'">
              MMA
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Politics'}" v-on:click="subjectTagged = 'Politics'">
              Politics
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Movies'}" v-on:click="subjectTagged = 'Movies'">
              Movies
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'In Theaters'}" v-on:click="subjectTagged = 'In Theaters'">
              In Theaters
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Music'}" v-on:click="subjectTagged = 'Music'">
              Music
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Tv'}" v-on:click="subjectTagged = 'Tv'">
              TV
            </button>
            <button class="tag-button" v-bind:class="{tagButtonClicked: subjectTagged == 'Just For Friends'}" v-on:click="subjectTagged = 'Just For Friends'">
              Just For Friends
            </button>
            </div>
                <div class="buttons">
                     <button class="barber-button" v-on:click="postComment()">Post</button>
                        <span class="character-count">{{remainingCharacters}}/140 remaining</span>
                </div>
        </div>

        <h1>My Thoughts: </h1>
    <div class="status-container">
            <div class = "status" v-for="status in firstSixStatuses" v-bind:key="status.id">
                <div class="username-date">
                    <h4>@{{status.username}}</h4> 
                    <h6>{{status.date}}</h6>
                </div>
                <h6 class="status-text">{{ status.status }}</h6>
                <div class="status-buttons"> 
                  <div>
                  <p v-if="mapLikeCount[status.id] > 0"> Like Count: {{ mapLikeCount[status.id] }}</p>
                  <p v-if="mapLikeCount[status.id] == null"> Like Count: 0</p>
                  <p v-if="mapDislikeCount[status.id] > 0"> Dislike Count: {{ mapDislikeCount[status.id] }}</p>
                  <p v-if="mapDislikeCount[status.id] == null"> Dislike Count: 0</p>
                </div>
                  <button v-on:click="deleteStatus(status.id)"><i class="fa fa-solid fa-trash"></i></button>
                </div>
            </div>
        </div>
        <div class="show-more-button" v-on:click="initialStatuses = userStatuses.length" v-if="initialStatuses == 6"> 
          <h4>Show More </h4> <i class="fa fa-solid fa-caret-down" style="color: #3b9aed"></i>
        </div>
        <div class="show-more-button" v-on:click="initialStatuses = 6" v-if="initialStatuses !== 6"> 
          <h4>Show Less </h4> <i class="fa fa-solid fa-caret-up" style="color: #3b9aed"></i>
        </div>
    </div>
    </template>
    
    <script>

import ArticleDataService from '../services/ArticleDataService';
import StatusService from '../services/StatusService.js';
  
    export default {
      name: "userStatuses",
      components: {
      },
      data() {
        return {
          //current user logged ins id/name
          currentUser: 0,
          currentUsername: '',
          //initial 6
          initialStatuses: 6,
          //textarea
          status: '',
          maxLength: 140,
          //not sure how but im hoping to capture a picture file for memes and so on in the future here
          mediaFile: '',
          //all current users statuses
          userStatuses: [],
          likeCount:[],
          dislikeCount: [],
          subjectTagged: ''
        }
      },

      async mounted() {
      //calls for current user
      ArticleDataService.getUser().then(response => {
            this.currentUser = response.data
      }),
      ArticleDataService.getUsername().then(response => {
            this.currentUsername = response.data
      }),
      StatusService.getStatuses().then(response => {
        this.userStatuses = response.data
      }),
      StatusService.getLikeCount().then(response => {
        this.likeCount = response.data
      }),
      StatusService.getDislikeCount().then(response => {
        this.dislikeCount = response.data
      })
    },  
    computed: {
      remainingCharacters(){
        return this.maxLength - this.status.length
      },
      mapLikeCount(){
    return this.likeCount.reduce((acc, curr) => {
      acc[curr.status_id] = curr.count;
      return acc;
      }, {});
    },
    mapDislikeCount(){
    return this.dislikeCount.reduce((acc, curr) => {
      acc[curr.status_id] = curr.count;
      return acc;
      }, {});
    },
    firstSixStatuses(){
      return this.userStatuses.slice(0, this.initialStatuses)
    }

},
      methods: {
            textAreaCharactersAdd(){
                this.length = this.status.length
            },
            stopType(event) {
                if (this.status.length >= this.maxLength && event.keyCode !== 8) {
                    event.preventDefault();
                }
            },
            postComment(){
              if(this.status !== '' && this.subjectTagged !== ''){
                
                const now = new Date();
                const isoString = now.toISOString();
                const datePart = isoString.slice(0, 10);
                const timePart = isoString.slice(11, 19);
                const formattedString = `${datePart}T${timePart}Z`;
                let thought = 
                  {
                    date: formattedString,
                    status: this.status,
                    media_file: this.mediaFile,
                    subject_tagged: this.subjectTagged
                  }
                  StatusService.postStatus(thought).then(response => {
                    
          if(response.status == 200){
            let thoughtToAdd = 
                  {
                    id: this.currentUser,
                    username: this.currentUsername,
                    date: formattedString,
                    status: this.status,
                    media_file: this.mediaFile,
                    subject_tagged: this.subjectTagged
                  }
                  this.status = ''
                  this.subjectTagged = ''
            this.userStatuses.unshift(thoughtToAdd)
          }
        })                 
              } else{
                alert('Please Check and make sure the text box is not blank and that you have tagged your status')
              }
            },
            deleteStatus(id){
              StatusService.deleteStatus(id).then(response => {
                if(response.status == 200){
                  let index = this.userStatuses.findIndex(status => status.id === id);
                  if (index > -1) { // only splice array when item is found
                    this.userStatuses.splice(index, 1); // 2nd parameter means remove one item only
                  }
                } else {
                  console.log('No Thought Detected')
                }
              })
            }
    },
}
    
    
    
    </script>
    
    <style scoped>

    /* TAGS */
    textarea{
    font-family: Arial, Helvetica, sans-serif;
    box-sizing: border-box;
    border: 2px solid #ccc;
    border-radius: 4px;
    background-color: #f8f8f8;

    }

    i:hover{
  color: red;
  cursor: pointer;
}

button{
  background: none;
  border: none;
}

.show-more-button{
  display: flex;
  align-items: center;
  justify-content: center;
}

    /* STATUSES */

    .status{
      cursor: pointer;
    }
    .status-container{
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;
        grid-row: inherit;
        margin-bottom: 1em;
        gap: 1em;
        padding: 1em;
    }


    .status{
        background-color: #d8e9f8;
        animation: fadeInBottom 0.5s ease-in;
  }

  @keyframes fadeInBottom {
    from {
        transform: translateY(100%);
        opacity: 0;
    }
    to {
        transform: translateY(0);
        opacity: 1;
    }
  }

    .status:hover,
  .status.active{
    background-color: #d8e9f8;
    transition: border-bottom 0.3s ease, color 0.3s ease;
    transform: scale(1.01);
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    opacity: 1;
  }
    .username-date{
        display: flex;
        justify-content: space-around;
    }

    .status-text{
        font-size: 13px;
        margin-top: 0em;
        margin-bottom: 1em;
    }

    /* STATUS UPDATE */
    .status-update.barber-theme {
  background-color: #7db4e3;
  border: 1px solid #dd3434;
  padding: 10px;
}

.status-update.barber-theme img {
  border-radius: 50%;
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.status-update.barber-theme .barber-input {
  font-size: 16px;
  padding: 10px;
  border: none;
  border-bottom: 2px solid #dd3434;
  width: 80%;
}

.status-update.barber-theme .buttons {
  margin-top: 10px;
}

.status-update.barber-theme .barber-button {
  background-color: #dd3434;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-right: 10px;
}

.tag-button{
  background-color: #3a6ed7;
  color: white;
  border: none;
  padding: 5px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin: 4px;
}

.tag-button:hover{
  background-color: #053ba7;
}
.tagButtonClicked{
  background-color: #053ba7;
  color: white;
  border: none;
  padding: 5px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin: 4px;
}

.status-update.barber-theme .character-count {
    padding: 1em;
  font-size: 14px;
  color: #fff;
}

.status-buttons{
  display: flex;
  justify-content: space-evenly;
  padding: 1em;
}

.status-text{
  font-size: 16px;
}




    @media (max-width: 1300px) {
        .status-container{
            justify-content: center;
        grid-template-columns: 1fr 1fr;
        }
    }

    @media (max-width: 693px) {
        .status-container{
        justify-content: center;
        grid-template-columns: 1fr;
        gap: .1em;
        }

  
    }

    </style>