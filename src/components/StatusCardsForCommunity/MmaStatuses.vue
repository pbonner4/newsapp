<template>
  <div class="status-sidebar">
    <div class="status-container">
      <div class = "status" v-for="status in displayStatuses" v-bind:key="status.id">
                <div class="username-date">
                  <div class="user-follow-container">
                    <h4>@{{ status.username }}</h4> 
                    <!-- v-if="!peopleFollowing.user_followed.includes(status.username) || status.username == currentUsername" -->
                    <button v-on:click=" user_followed = status.username; user_followed_id = status.user_id; followAUser()" v-if="!peopleFollowing.some(f => f.user_followed === status.username || f.user_following === status.username)" class="follow-button">follow?</button>
                    <button v-on:click="unfollowAUser(status.user_id)" v-if="peopleFollowing.some(f => f.user_followed === status.username)" class="following-button">Following</button>
                  </div>
                    <h6>{{ status.date }}</h6>
                </div>
                <h6 class="status-text">{{ status.status }}</h6>
                <div class="status-buttons"> 
                  <i class="fa fa-regular fa-thumbs-up" v-bind:class="{'fa fa-solid fa-thumbs-up i-changed': checkIfLiked(status.id)}" v-on:click="status_id = status.id; likeAPost()"> {{mapLikeCount[status.id]}}</i>
                  <i class="fa fa-regular fa-thumbs-down" v-bind:class="{'fa fa-solid fa-thumbs-down i-changed': checkIfDisLiked(status.id)}" v-on:click="status_id = status.id; dislikeAPost()"> {{mapDislikeCount[status.id]}}</i>
                </div>
            </div>
    </div>
  </div>
</template>

<script>
import StatusService from '@/services/StatusService';
import ArticleDataService from '@/services/ArticleDataService';
import FriendRequestService from '@/services/FriendRequestService';


export default {
name: "BusinessStatuses",
data() {
          return {
              currentUsername: '',
              currentUserId: 0,
              status_id: 0,
              liked: true,
              likedStatuses: [],
              likeCount: {},
              dislikedStatuses: [],
              disliked: true,
              dislikeCount: {},
              statuses: [],
              peopleFollowing: [],
              user_followed: '',
              user_followed_id: 0,
              start: 0,
                end: 12
          }
      },
components: {
},
async mounted() {
  window.addEventListener('scroll', this.handleScroll)
  StatusService.listLikedStatuses().then(response => {
    this.likedStatuses = response.data
  }),
  StatusService.getLikeCount().then(response => {
    this.likeCount = response.data
  }),
  StatusService.getDislikeCount().then(response => {
    this.dislikeCount = response.data
  }),
  StatusService.listDislikedStatuses().then(response => {
    this.dislikedStatuses = response.data
  }),
  StatusService.getMmaStatuses().then(response => {
      this.statuses = response.data
  })
  ArticleDataService.getUsername().then(response => {
    this.currentUsername = response.data
  }),
  ArticleDataService.getUser().then(response => {
    this.currentUserId = response.data
  }),
  FriendRequestService.getPeopleFollowing().then(response => {
    this.peopleFollowing = response.data
  })
},
computed: {
  displayStatuses(){
        return this.statuses.slice(this.start,this.end)
      },
      listFriendsStatuses(){
        if (!this.currentUsername) {
      return []
      }
        return this.friendsStatuses.filter(status => status.username !== this.currentUsername)
      },
      checkIfLiked(){
      return id => this.likedStatuses.some(like => like.status_id === id);
      },
      checkIfDisLiked(){
      return id => this.dislikedStatuses.some(dislike => dislike.status_id === id);
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
},
methods: {
  handleScroll() {
       let scrollY = window.scrollY;
       let innerHeight = window.innerHeight;
       let docHeight = document.body.offsetHeight;
       if (scrollY + innerHeight >= docHeight * 0.4) {
         this.end+=12
       }
      },
  followAUser(){
    let follow = {
                user_following: this.currentUsername,
                user_id_following: this.currentUserId,
                user_followed: this.user_followed,
                user_followed_id: this.user_followed_id
              }
    FriendRequestService.followSomeone(follow).then(response => {
        if(response.status == 200){
          console.log("followed")
          this.peopleFollowing.push(follow)
        } else {
          alert('Follow coult not be complete, please try again later')
        }
      })
  },
  unfollowAUser(followedId){
    FriendRequestService.unFollowSomeone(followedId).then(response => {
      if(response.status == 200){
        let index = this.peopleFollowing.findIndex(user => user.user_followed_id === followedId);
                if (index > -1) { // only splice array when item is found
                  this.peopleFollowing.splice(index, 1); // 2nd parameter means remove one item only
                }
        console.log("unfollowed")
      } else {
        alert('Unfollow coult not be complete, please try again later')
      }
    })
  },
  likeAPost(){

    if(this.likedStatuses.some(status => status.status_id === this.status_id)){
          StatusService.unlikeAStatus(this.status_id).then(response=> {
            if(response.status === 200 ){
              this.likedStatuses = this.likedStatuses.filter(status => status.status_id !== this.status_id);
            }
          })
        } if(this.dislikedStatuses.some(status => status.status_id === this.status_id)){
      StatusService.undislikeAPost(this.status_id).then(response=> {
        if(response.status === 200 ){
          this.dislikedStatuses = this.dislikedStatuses.filter(status => status.status_id !== this.status_id);
        }
      })
      let like = {
      "user_id": this.currentUserId,
      "status_id": this.status_id,
      "liked": this.liked
    }
    StatusService.likeAStatus(like).then(response =>{
      if(response.status == 200){
        this.likedStatuses.push(like)
        console.log(JSON.stringify(this.likedStatuses))
      }
    })
    } else {

    let like = {
      "user_id": this.currentUserId,
      "status_id": this.status_id,
      "liked": this.liked
    }
    StatusService.likeAStatus(like).then(response =>{
      if(response.status == 200){
        this.likedStatuses.push(like)
        console.log(JSON.stringify(this.likedStatuses))
      }
    })
  }
  },
  dislikeAPost(){

    if(this.dislikedStatuses.some(status => status.status_id === this.status_id)){
      StatusService.undislikeAPost(this.status_id).then(response=> {
        if(response.status === 200 ){
          this.dislikedStatuses = this.dislikedStatuses.filter(status => status.status_id !== this.status_id);
        }
      })
    } if(this.likedStatuses.some(status => status.status_id === this.status_id)){
          StatusService.unlikeAStatus(this.status_id).then(response=> {
            if(response.status === 200 ){
              this.likedStatuses = this.likedStatuses.filter(status => status.status_id !== this.status_id);
            }
          })
          let dislike = {
            "user_id": this.currentUserId,
            "status_id": this.status_id,
            "liked": this.disliked
          }
          StatusService.dislikedAStatus(dislike).then(response =>{
          if(response.status == 200){
              this.dislikedStatuses.push(dislike)
          }
        })
        }
     else {

let dislike = {
  "user_id": this.currentUserId,
  "status_id": this.status_id,
  "liked": this.disliked
}
StatusService.dislikedAStatus(dislike).then(response =>{
  if(response.status == 200){
    this.dislikedStatuses.push(dislike)
  }
})
}
},
}
}
</script>


<style scoped>
i {
  cursor: pointer;
  margin-right: 3px;
}
    i:hover{
  color: #087bdf;
  cursor: pointer;
  margin-right: 1px;
}

.i-changed{
  color: #087bdf;
  cursor: pointer;
  margin-right: 1px;
}


    /* STATUSES */
    .status-container{
        gap: 1em;
        padding: 3% 3% 3% 3%;
          display: grid;
        grid-template-columns: repeat(4, minmax(250px, 1fr));
      grid-auto-rows: minmax(250px, auto);
      grid-auto-flow: dense;
      background-color: #eeeff1;
      
    }

    @media (max-width: 916px){
      .status-container{
        margin: .5em;
        gap: 1em;
        padding: 3% 3% 3% 3%;
          display: grid;
        grid-template-columns: repeat(2, minmax(250px, 1fr));
      grid-auto-rows: minmax(250px, auto);
      grid-auto-flow: dense;
    }
    }
    @media (max-width: 615px){
      .status-container{
        margin: .5em;
        gap: 1em;
        padding: 3% 3% 3% 3%;
          display: grid;
        grid-template-columns: repeat(1, minmax(250px, 1fr));
      grid-auto-rows: minmax(250px, auto);
      grid-auto-flow: dense;
    }
    }


    .status{
        background-color: #ffffff;
        padding: 1em;
        animation: fadeInBottom 0.5s ease-in;
        color: rgb(13, 13, 13);
        border: 1px solid #3b9aed;
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
    transition: border-bottom 0.3s ease, color 0.3s ease;
    transform: scale(1.01);
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    opacity: 1;
  }
    .username-date{
        display: flex;
        justify-content: space-around;
    }

    .user-follow-container{
      display: flex;
      flex-direction: row;
      align-items: center;
      cursor: pointer;
    }

    .follow-button{
      height: fit-content;
      margin-left: .5em;
      font-family: Arial, Helvetica, sans-serif;
      color: #ffffff;
      background-color: #4397e0;
      border: .5px solid;
      border-image: none;
      cursor: pointer;
    }

    .following-button{
      height: fit-content;
      margin-left: .5em;
      font-family: Arial, Helvetica, sans-serif;
      color: #ffffff;
      background-color: #e04343;
      border: none;
      cursor: pointer;
    }

    .status-text{
        font-size: 18px;
        margin-top: 0em;
    }

    .status-buttons{
  display: flex;
  justify-content: space-evenly;
  cursor: pointer;
}


</style>