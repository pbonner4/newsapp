<template>
    <div>
        <div  v-for="event in events.events" :key="event.id"> 
            <h1 style="margin: 0px">{{ event.name }}</h1>

            <div class="event-details"> 
                <div>
                    <p style="margin: 0px">{{ event.competitions[0].venue.fullName }}</p>
                    <p style="margin: 0px">{{ event.competitions[0].venue.address.city }} , {{ event.competitions[0].venue.address.state }}</p>
                </div>
                <p style="margin: 0px">See It On: {{ event.competitions[0].broadcasts[0].names }}</p>
            </div>

            <div class="show-more-button" v-on:click="showCard = true;" v-show="showCard==false"> 
                <h4 class="show-more-button">Show Card </h4> <i class="fa fa-solid fa-caret-down" style="color: #3b9aed"></i>
            </div>
            <div class="show-more-button" v-on:click="showCard = false;" v-show="showCard==true"> 
                <h4 class="show-more-button">Hide Card </h4> <i class="fa fa-solid fa-caret-up" style="color: #3b9aed"></i>
            </div>
    <div class="games-container" v-show="showCard"> 
            <div class="games-grid" v-for="(competition) in event.competitions" :key="competition.uid"> 
                <div class="competitors"> 
                    <div>  
                        <h4 class="athlete-name">{{ competition.competitors[0].athlete.fullName }}</h4>
                        <p v-show="competition.status.type.completed && competition.competitors[0].winner" style="color: green">W</p>
                        <p v-show="competition.status.type.completed && !competition.competitors[0].winner" style="color: red">L</p>
                        <p>{{ competition.competitors[0].records[0].summary }}</p>
                            <img :rel="competition.competitors[0].athlete.flag.rel" :alt = "competition.competitors[0].athlete.flag.alt"  :src="competition.competitors[0].athlete.flag.href" class="flags"/>
                    </div>
                    <div> 
                        <h6 v-show="competition.status.period > 0" style="margin:0px"> round {{ competition.status.period }}</h6>
                        <p style="margin:0px">{{ competition.status.displayClock }}</p>
    
                    </div>
                
                <div> 
                    <h4 class="athlete-name">{{ competition.competitors[1].athlete.fullName }}</h4>
                    <p v-show="competition.status.type.completed && competition.competitors[1].winner" style="color: green">W</p>
                        <p v-show="competition.status.type.completed && !competition.competitors[1].winner" style="color: red">L</p>
                    <p>{{ competition.competitors[1].records[0].summary }}</p>
                    <img :rel="competition.competitors[1].athlete.flag.rel" :alt = "competition.competitors[1].athlete.flag.alt"  :src="competition.competitors[1].athlete.flag.href" class="flags"/>
                </div>
                </div>
                <div> 
                    <p style="margin: 0px"> {{ competition.type.abbreviation }} </p>
                </div>
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
              showCard: false
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
          }
      },
  
  
  }
  </script>
  
  <style scoped>
  
  
  
  
  .games-container{
      height: fit-content;
      display: grid;
      grid-template-columns: repeat(3, minmax(250px, 1fr));
      grid-auto-rows: minmax(250px, auto);
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

  .event-details{
    display: flex;
    justify-content: space-around;
  }

  .competitors{
    display: flex;
    align-items: center;
    justify-content: space-around;
  }
  
  .flags{
    height: 40%;
    width: 30%;
    margin: 0px;
  }

  .athlete-name{
    margin: 0px;
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