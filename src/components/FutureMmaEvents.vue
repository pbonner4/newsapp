<template>
    <div>
        <div class="future-mma-events-container"  v-for="event in events.events" :key="event.id"> 

            <div class="show-more-button" v-on:click="showCard = true;" v-show="showCard==false"> 
                <h4 class="show-more-button">Show Future Events </h4> <i class="fa fa-solid fa-caret-down" style="color: #3b9aed"></i>
            </div>
            <div class="show-more-button" v-on:click="showCard = false;" v-show="showCard==true"> 
                <h4 class="show-more-button">Hide Future Events </h4> <i class="fa fa-solid fa-caret-up" style="color: #3b9aed"></i>
            </div>
    <div class="games-container" v-show="showCard" v-for="league in events.leagues" :key="league.uid"> 
            <div class="games-grid" v-for="event in league.calendar" :key="event.label" v-show="isFutureEvent(event)" v-bind:class="{importantMatch: !event.label.includes('Fight Night')}" >
                <h4 >{{ event.label }}</h4>
                <h4>{{ event.startDate.slice(0,10) }}</h4>
        </div>
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
              showTeamStats: false,
              activeIndex: null,
              showCard: false,
              today: new Date()
          }
      },
      async mounted() {
          LiveScores.listMmaScores().then(response => {
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
          },
    isFutureEvent(event) {
      const startDate = new Date(event.startDate)
      return startDate >= new Date()
    },
  },
  }
  </script>
  
  <style scoped>
  
  
  .future-mma-events-container{
    background-color: #d6e0f5;
  }
  
  .games-container{
      height: fit-content;
      display: grid;
      grid-template-columns: repeat(3, minmax(250px, 1fr));
      grid-auto-rows: minmax(auto, auto);
      grid-auto-flow: dense;
      gap: 20px;
      padding: 1%;
      background-color: #eeeff1;
      align-content: center;
  }
  
  
  .games-grid{
      border: 1px solid #3b9aed;
      background-color: #e0e1e2;
      height: fit-content;
  }

  .importantMatch{
      border: 1px solid #ff0000;
      background-color: #c6c6c6;
      font-weight: 900;
      height: fit-content;

  }
  
  
  @media (max-width: 916px){
      .games-container{
      grid-template-columns: repeat(2, minmax(auto, 1fr));
      }
  }
  
  @media (max-width: 712px){
      .games-container{
      grid-template-columns: repeat(1, minmax(auto, 1fr));
      }
  }

  .show-more-button{
  display: flex;
  align-items: center;
  justify-content: center;
  height: fit-content;
  margin: 5px 0px 5px 0px;
  cursor: pointer;
}

  
  </style>