<template>
    <div>
      <h2>Games Today: </h2>
      <div class="games-container"> 
          <div class="games-grid" v-for="(event, index) in events.events" :key="event.id"> 
              <h3  style="margin: 0px">{{ event.shortName }}</h3>
          <div class = "logos-records-container">
              <div class="logos-records"> 
                  <img class="logos" :src="event.competitions[0].competitors[1].team.logo"/>
                  <p style="width: max-content"> {{ event.competitions[0].competitors[1].records[0].summary }}</p>
              </div>
              <p>VS</p>
              <div class="logos-records">
                  <img class="logos" :src="event.competitions[0].competitors[0].team.logo"/>
                  <p style="width: max-content"> {{ event.competitions[0].competitors[0].records[0].summary }}</p>
              </div>
          </div>
              <div class="scores"> 
                  <div> 
  
                      <p v-if="event.competitions[0].competitors[0].score > 0 || event.competitions[0].competitors[1].score > 0"> <strong>{{ event.competitions[0].competitors[1].score }}</strong></p>
                  </div>
                  <div> 
  
                      <p class="time-status">
                          <strong>{{event.competitions[0].status.type.detail}} </strong>
                      </p>
                      <p class="tv-options">On: {{ event.competitions[0].geoBroadcasts[0].media.shortName }}</p>
                  </div>
                  <div> 
  
                      <p v-if="event.competitions[0].competitors[0].score > 0 || event.competitions[0].competitors[1].score > 0"><strong>{{ event.competitions[0].competitors[0].score }}</strong></p>
                  </div>
              </div>
              <div class="show-more-button" v-on:click="showTeamLeaders = true; showDetails(index)" v-show="activeIndex == null"> 
                  <h4 class="show-more-button">Show Game Details </h4> <i class="fa fa-solid fa-caret-down" style="color: #3b9aed"></i>
              </div>
              <div class="show-more-button" v-on:click="showTeamLeaders = false; showDetails(index)" v-show="activeIndex == index"> 
                  <h4 class="show-more-button">Hide Game Details </h4> <i class="fa fa-solid fa-caret-up" style="color: #3b9aed"></i>
              </div>

              <h3 style="background-color: white; margin: 0px" v-if="activeIndex == index">Game Standouts</h3>
              <div class="team-leaders" v-if="activeIndex === index">
               <div class="leader-info-container"> 
                  <h6 style="margin: 0px">{{ event.competitions[0].leaders[0].leaders[0].athlete.displayName }}</h6>
                  <img class="headshots" :src="event.competitions[0].leaders[0].leaders[0].athlete.headshot"/>
                  <div class="stat"> 
                      <p> {{ event.competitions[0].leaders[0].leaders[0].displayValue }}</p>
                  </div>
              </div>
              <div class="leader-info-container"> 
                  <h6 style="margin: 0px">{{ event.competitions[0].leaders[1].leaders[0].athlete.displayName }}</h6>
                  <img class="headshots" :src="event.competitions[0].leaders[1].leaders[0].athlete.headshot"/>
                  <div class="stat"> 
                      <p> {{ event.competitions[0].leaders[1].leaders[0].displayValue }}</p>
                  </div>
                  <div class="leader-info-container"> 
                  <h6 style="margin: 0px">{{ event.competitions[0].leaders[2].leaders[0].athlete.displayName }}</h6>
                  <img class="headshots" :src="event.competitions[0].leaders[2].leaders[0].athlete.headshot"/>
                  <div class="stat"> 
                      <p> {{ event.competitions[0].leaders[2].leaders[0].displayValue }}</p>
                  </div>
                  </div>
              </div>
  
  
  
              
          </div>
          <h3 style="background-color: white" v-if="activeIndex == index"> Team Stats</h3>
          <!-- <div class="team-stats" v-if="activeIndex == index"> 
              <table>
                  <tr>
                      <th>{{event.competitions[0].competitors[1].team.abbreviation}}</th>
                      <th></th>
                      <th>{{event.competitions[0].competitors[0].team.abbreviation}}</th>
                  </tr>
                  <tr>
                      <th>{{ event.competitions[0].competitors[1].statistics[0].displayValue }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[0].abbreviation }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[0].displayValue }}</th>
                  </tr>
                  <tr>
                      <th>{{ event.competitions[0].competitors[1].statistics[2].displayValue }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[2].abbreviation }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[2].displayValue }}</th>
                  </tr>
                  <tr>
                      <th>{{ event.competitions[0].competitors[1].statistics[5].displayValue }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[5].abbreviation }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[5].displayValue }}</th>
                  </tr>
                  <tr>
                      <th>{{ event.competitions[0].competitors[1].statistics[6].displayValue }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[6].abbreviation }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[6].displayValue }}</th>
                  </tr>
                  <tr>
                      <th>{{ event.competitions[0].competitors[1].statistics[10].displayValue }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[10].abbreviation }}</th>
                      <th>{{ event.competitions[0].competitors[0].statistics[10].displayValue }}</th>
                  </tr>
              </table>
          </div> -->
          </div>
          
      </div>
    </div>
  </template>
  
  <script>
  
  import LiveScores from '@/services/LiveScores'
  
  export default {
      data(){
          return {
              events: [],
              showTeamLeaders: false,
              showTeamStats: true,
              activeIndex: null
          }
      },
      async mounted() {
          LiveScores.listNflScores().then(response => {
      this.events = JSON.stringify(response.data)
      this.events = JSON.parse(this.events)
      console.log(this.events)
    })
  
      },
      methods: {
          showDetails(index){
              if(this.activeIndex == index){
               
                  this.activeIndex = null
                  console.log(index)
              } else{
                  console.log(index)
                  this.activeIndex = index
              }
          }
      }
  
  
  }
  </script>
  
  <style scoped>
  
  
  table{
      width: 100%;
  }
  
  tr:nth-child(even) {
    background-color: #ffffff;
  }
  tr:nth-child(odd) {
    background-color: #d2d2d2;
  }
  
  .team-stats{
   display: flex;
   justify-content: center;
  }
  
  .games-container{
      height: auto;
      display: grid;
      grid-template-columns: repeat(3, minmax(250px, 1fr));
      grid-auto-rows: minmax(250px, auto);
      grid-auto-flow: dense;
      gap: 20px;
      padding: 1%;
      background-color: #eeeff1;
      align-content: center;
  }
  
  .logos-records-container{
      display: flex;
      justify-content: space-evenly;
  }
  
  .logos-records{
      height: min-content;
      width: min-content;
  }
  
  .games-grid{
      border: 1px solid #3b9aed;
      background-color: #e0e1e2;
  }
  
  .logos{
      height: 110%;
      width: 110%;
  }
  
  
  .scores{
      display: flex;
      justify-content: space-evenly;
  }
  
  @media (max-width: 916px){
      .games-container{
      grid-template-columns: repeat(2, minmax(250px, 1fr));
      }
  }
  
  @media (max-width: 712px){
      .games-container{
      grid-template-columns: repeat(1, minmax(250px, 1fr));
      }
  }
  
  .tv-options{
      font-size: small;
      margin: 5px 0px 0px 90px;
  }
  
  .time-status{
      margin-bottom: 0px;
  
  }
  
  .leader-info-container{
      display: flex;
      flex-direction: column;
      align-items: center;
  }
  
  .headshots{
      height: 5em;
      width: 6em;
  }
  
  .team-leaders{
      display: flex;
      justify-content: space-evenly;
      align-content: center;
  }
  
  .stat{
      display: flex;
      align-items: center;
  }
  
  .show-more-button{
    display: flex;
    align-items: center;
    justify-content: center;
    height: fit-content;
    margin: 5px 0px 0px 0px;
    cursor: pointer;
  }
  
  </style>