<template>
      <div class="liked-statuses-container">
                <h1 v-on:click="showLikedStatuses = true" v-show="showLikedStatuses == false">Show Liked Thoughts <i class="fa-solid fa-caret-right arrow"></i></h1>
                <h1 v-on:click="showLikedStatuses = false" v-show="showLikedStatuses == true">Hide Liked Thoughts <i class="fa-solid fa-caret-down arrow"></i></h1>
    <div class="liked-status-container" v-for="status in listLikedStatuses" v-bind:key="status.id" v-show="showLikedStatuses == true">
            <div class = "status">
                <div class="username-date">
                    <h4>{{ status.username }}</h4> 
                    <h6>{{ status.date }}</h6>
                </div>
                <h6 class="status-text">{{ status.status }}</h6>
                <div class="status-buttons"> 
                  <i class="fa fa-regular fa-thumbs-up i-thumbs-up">{{mapLikeCount[status.id]}}</i>
                  <i class="fa fa-regular fa-thumbs-down" v-on:click="status_id = status.id"></i>
                </div>
            </div>
    </div>
  </div>
</template>

<script>
import StatusService from '../services/StatusService'

export default {
name: "LikedStatuses",
data(){
        return {
            likedStatuses: [],
            allStatuses: [],
            showLikedStatuses: false,
            likeCount: []
        }
    },
    computed: {
        listLikedStatuses(){
  if (this.allStatuses.length > 0 && this.likedStatuses.length > 0) {
    return this.allStatuses.filter(status => {
      return this.likedStatuses.some(likedStatus => {
        return likedStatus.status_id === status.id; // added return statement
      });
    });
  } else {
    // Return a loading state or an empty array until the data is available
    return [];
  }
    },
    mapLikeCount(){
    return this.likeCount.reduce((acc, curr) => {
      acc[curr.status_id] = curr.count;
      return acc;
    }, {});
  }
},
    async mounted(){
    StatusService.getAllStatuses().then(response => {
            this.allStatuses = response.data
        }),
        StatusService.listLikedStatuses().then(response => {
        this.likedStatuses = response.data
    }),
    StatusService.getLikeCount().then(response => {
        this.likeCount = response.data
    })
    },
}
</script>

<style scoped>
.i-thumbs-up {
  cursor: pointer;
  margin-right: 3px;
  color: #087bdf;
}

.arrow{
    color: #087bdf;
    cursor: pointer;
}
.arrow:hover{
    color:red;
}


.liked-statuses-container{
        width: 100%;
        background-color: #eeeff1;
        margin-bottom: 100px;
    }


    /* STATUSES */
    .liked-status-container{
        margin-bottom: 1em;
        gap: 1em;
        padding: 1em;
    }


    .status{
        background-color: #ffcccc;
        padding: 1em;
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