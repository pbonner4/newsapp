<template>
  <div>
    <div class = "friends-personal-toggle">
                <h1 class="heading-holder">
                    <div v-on:click="friendsOrPersonal = false" v-bind:class="{grayedHeader: friendsOrPersonal}">Your Thoughts </div> 
                    <div v-on:click="friendsOrPersonal = true" v-bind:class="{grayedHeader: !friendsOrPersonal}"> Friends Thoughts</div> 
                </h1>
            </div>
    <userStatuses v-if="!friendsOrPersonal"></userStatuses>
    <StatusSidebar v-if="friendsOrPersonal" style="width: 100%"></StatusSidebar>
    <userSaveForLaterPosts v-if="!friendsOrPersonal"></userSaveForLaterPosts>
    <userLikedPosts v-if="!friendsOrPersonal"></userLikedPosts>
    <userDislikedPosts v-if="!friendsOrPersonal"></userDislikedPosts>
    <LikedStatuses v-if="!friendsOrPersonal"></LikedStatuses>
  </div>
</template>

<script>
import userStatuses from './userStatuses.vue';
import StatusSidebar from '@/components/StatusSidebar.vue';
import userSaveForLaterPosts from './userSaveForLaterPosts.vue';
import userLikedPosts from './userLikedPosts.vue';
import userDislikedPosts from './userDislikedPosts.vue';
import LikedStatuses from './LikedStatuses.vue';


export default {
name:"profileDashboardMobile",
components: {
    userStatuses,
    StatusSidebar,
    userSaveForLaterPosts,
    userLikedPosts,
    userDislikedPosts,
    LikedStatuses
},
data(){
    return {
        friendsOrPersonal: false
    }
} 
    

}
</script>

<style scoped>

.heading-holder{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}

 .grayedHeader{
        color:lightgray;
        cursor: pointer;
    }
</style>