<template>
    <div class="community-container">
  <div class="status-grid">
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Following'" v-bind:class="{clickedHeader: subjectClicked == 'Following'}">| Following |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Business'" v-bind:class="{clickedHeader: subjectClicked == 'Business'}">| Business |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Nba'" v-bind:class="{clickedHeader: subjectClicked == 'Nba'}">| Nba |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Nfl'" v-bind:class="{clickedHeader: subjectClicked == 'Nfl'}">| Nfl |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Nhl'" v-bind:class="{clickedHeader: subjectClicked == 'Nhl'}">| Nhl |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'MMA'" v-bind:class="{clickedHeader: subjectClicked == 'MMA'}">| MMA |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Politics'" v-bind:class="{clickedHeader: subjectClicked == 'Politics'}">| Politics |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Movies'" v-bind:class="{clickedHeader: subjectClicked == 'Movies'}">| Movies |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'In Theaters'" v-bind:class="{clickedHeader: subjectClicked == 'In Theaters'}">| In Theaters |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'Music'" v-bind:class="{clickedHeader: subjectClicked == 'Music'}">| Music |</h1>
    <h1 class="grayedHeader" v-on:click="subjectClicked = 'TV'" v-bind:class="{clickedHeader: subjectClicked == 'TV'}">| TV |</h1>

</div>

    <BusinessStatusesVue v-show="subjectClicked == 'Business'"></BusinessStatusesVue>
    <InTheatersStatuses v-show="subjectClicked == 'In Theaters'"></InTheatersStatuses>
    <MmaStatuses v-show="subjectClicked == 'MMA'"></MmaStatuses>
    <MoviesStatuses v-show="subjectClicked == 'Movies'"></MoviesStatuses>
    <MusicStatuses v-show="subjectClicked == 'Music'"></MusicStatuses>
    <NbaStatusesVue v-show="subjectClicked == 'Nba'"></NbaStatusesVue>
    <NflStatusesVue v-show="subjectClicked == 'Nfl'"></NflStatusesVue>
    <NhlStatuses v-show="subjectClicked == 'Nhl'"></NhlStatuses>
    <PoliticsStatuses v-show="subjectClicked == 'Politics'"></PoliticsStatuses>
    <TvStatuses v-show="subjectClicked == 'TV'"></TvStatuses>
    <PeopleFollowingStatuses v-show="subjectClicked == 'Following'"></PeopleFollowingStatuses>


</div>

</template>

<script>
import BusinessStatusesVue from './StatusCardsForCommunity/BusinessStatuses.vue'
import InTheatersStatuses from './StatusCardsForCommunity/InTheatersStatuses.vue';
import MmaStatuses from './StatusCardsForCommunity/MmaStatuses.vue';
import MoviesStatuses from './StatusCardsForCommunity/MoviesStatuses.vue';
import MusicStatuses from './StatusCardsForCommunity/MusicStatuses.vue';
import NbaStatusesVue from './StatusCardsForCommunity/NbaStatuses.vue';
import NflStatusesVue from './StatusCardsForCommunity/NflStatuses.vue';
import NhlStatuses from './StatusCardsForCommunity/NhlStatuses.vue';
import PoliticsStatuses from './StatusCardsForCommunity/PoliticsStatuses.vue';
import TvStatuses from './StatusCardsForCommunity/TvStatuses.vue';
import PeopleFollowingStatuses from './StatusCardsForCommunity/PeopleFollowingStatuses.vue';


export default {
name: "CommunityStatuses",
components: {
    BusinessStatusesVue,
    InTheatersStatuses,
    MmaStatuses,
    MoviesStatuses,
    MusicStatuses,
    NbaStatusesVue,
    NflStatusesVue,
    NhlStatuses,
    PoliticsStatuses,
    TvStatuses,
    PeopleFollowingStatuses
},
data(){
    return {
        subjectClicked: 'Following'
    }
}

}
</script>

<style scoped>
.community-container{
    width: 85%;
}

.status-grid{
      width: 84vw;
      height: fit-content;
      background-color: #eeeff1;
      align-content: center;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-evenly;
    }

    @media (max-width: 916px){
        .status-grid{
            width: 100%;
        }
        .community-container{
    width: 100%;
    font-size: small;
} 
    }
    .grayedHeader{
        color:rgb(201, 199, 199);
        cursor: pointer;
    }

    .clickedHeader{
        color: black;
        cursor: pointer;
    }

</style>