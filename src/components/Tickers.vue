<template>
  <div>
    <div class="ticker-container">
      <div> 
        <h1 class="snapshot-header">Weekly Economic Snapshot</h1>
      </div>
      <div>
      <div class="stats-for-selected-ticker" v-if="showtickerResultInfo"> 

        <h4>{{ tickerResult['01. symbol'] }}</h4>
        <div class="percentage-change"> <h4 class="percentage-change"> Percentage Change: 
          <i id="up-down-caret-percentage-change" class="fa-solid fa-caret-up"
            v-if="!downOrUp2(tickerResult['10. change percent'])"
            :class="{ 'up-down-caret-green': !downOrUp2(tickerResult['10. change percent']) }">
           </i>
          <i id="up-down-caret-percentage-change"
            class="fa-solid fa-caret-down"
            v-else
            :class="{ 'up-down-caret-red': downOrUp2(tickerResult['10. change percent']) }">
          </i>
          {{Math.round( parseInt(tickerResult['10. change percent'].replaceAll(/%-/g, '')) * 100) / 100}} %</h4> 
        </div>
          <h4>Open: {{ tickerResult['02. open'] }}</h4>
          <h4>High: {{ tickerResult['03. high'] }}</h4>
          <h4>Low: {{ tickerResult['04. low'] }}</h4>
          <h4>Previous Close: {{ tickerResult['08. previous close'] }}</h4>
      </div>
    </div>
      <div>
      <input class="ticker-searchbar" type="search" placeholder="Search By Ticker..." v-model="symbol" v-on:keyup.enter="tickerSearch();">

        <div class="ticker-search-results" v-if="tickerInfo.bestMatches"> 
        <div class="search-bar-container"  v-for="(ticker, index) in tickerInfo.bestMatches" :key="index"> 

          <h4 v-on:click="selectedSymbol = ticker['1. symbol']; getSearchedTickerInfo(); showtickerResultInfo = true; tickerInfo = {}">{{ ticker['2. name'] }}</h4>
          <h4 v-on:click="selectedSymbol = ticker['1. symbol']; getSearchedTickerInfo(); showtickerResultInfo = true; tickerInfo = {}">{{ ticker['1. symbol'] }}</h4>
          <h4 v-on:click="selectedSymbol = ticker['1. symbol']; getSearchedTickerInfo(); showtickerResultInfo = true; tickerInfo = {}">{{ ticker['4. region'] }}</h4>

        </div>
      </div>
    </div>
    <div class="tickers">
        <div v-for="ticker in tickerPercentages" v-bind:key="ticker.index" class = "tickers" >
        <div>
            <h2>{{ticker.index}}</h2>
        </div>
        <i id="up-down-caret"
   class="fa-solid fa-caret-up"
   v-if="!downOrUp(ticker.percentage_change)"
   :class="{ 'up-down-caret-green': !downOrUp(ticker.percentage_change) }">
        </i>
<i id="up-down-caret"
   class="fa-solid fa-caret-down"
   v-else
   :class="{ 'up-down-caret-red': downOrUp(ticker.percentage_change) }">
</i>
        <div style = "margin-left: 10px; margin-top: 5px;" > <h3>{{Math.round(ticker.percentage_change * 100) / 100}} %</h3> </div>
    </div>
  </div>
    </div>
  </div>
  </template>
  
  <script>
import CommoditiesService from '@/services/CommodityService'
import axios from 'axios'

  export default {
    name: 'TickerPercentages',
    data() {
      return {
        tickerPercentages: [],
        symbol: '',
        tickerInfo: {},
        showtickerResultInfo: false,
        selectedSymbol: '',
        tickerResult: {},
        globalQuote: "Global Quote"
        }
      },
    async mounted() {
      this.checkSymbol()
        CommoditiesService.listGeneralTickers().then(response => {
      this.tickerPercentages = response.data;
    })
    },
    methods: {
        downOrUp(num){
            return JSON.stringify(num).includes("-")
        },
        downOrUp2(num){
            return JSON.stringify(num).includes("-")
        },
        tickerSearch(){
          axios.get(`https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=${this.symbol}&apikey=UAFWD42XW9HP9G7C`)
                  .then(response => {
                  // Handle the response from the API endpoint here
                      this.tickerInfo = response.data 
                      console.log(this.tickerInfo)
                    })
                  .catch(error => {
                // Handle any errors that occurred during the request here
                  console.error(error);
                })
        },
        getSearchedTickerInfo(){
          axios.get(`https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${this.selectedSymbol}&apikey=UAFWD42XW9HP9G7C`)
                  .then(response => {
                  // Handle the response from the API endpoint here
                      this.tickerResult = response.data['Global Quote']
        })
                  .catch(error => {
                // Handle any errors that occurred during the request here
                  console.error(error);
                })
        },
        checkSymbol(){
          console.log(this.symbol)
        }
    }
  }
  </script>

  <style scoped>

.ticker-container{
    display: flex;
    justify-content: center;
    flex-direction: column;
    flex-wrap: wrap;
    background-color: rgb(237, 234, 234);
    border-top: 1px solid #dd3434;
    border-bottom: 1px solid #dd3434;
}
.tickers{
    display: flex;
    justify-content: space-evenly;
    flex-direction: row;
    flex-wrap: wrap;
    align-items: center;
}

@media (max-width: 916px){
  .tickers{
    font-size: small;
}

#up-down-caret {
    margin-top: 0px;
    margin-left: 5px;
}

.up-down-caret-green {
    color: green
}
.up-down-caret-red {
    margin-left: 5px;
    color: red;
}

.tickers{
  align-items: center;
}

.snapshot-header{
  font-size: x-large;
}

.ticker-searchbar{
  width: auto;
}

}


.ticker-searchbar{
  margin: 0 auto;
  display: flex;
  justify-content: center;
}

.percentage-change{
  display: flex;
}
.ticker-search-results{
    position: absolute;
    z-index: 9999;
    background-color: #fff;
    border: 1px solid #ccc;
    box-shadow: 0 2px 4px rgb(0 0 0 / 20%);
    margin-left: 22.5%;
    width: 25em;
    display: flex;
    flex-direction: column;
}

.stats-for-selected-ticker{
  display: flex;
  justify-content: space-evenly;
  height: fit-content;
  margin-bottom: 1em;
  align-items: center;
  flex-wrap: wrap;
  background-color: #c0ddf7;
  transition: all 0.3s ease;
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
.search-bar-container{
  display: flex;
    justify-content: space-evenly;
    border: 1px solid black;
    align-items: center;
    padding: 5px;
    cursor: pointer;
}

#up-down-caret {
    margin-left: 5px;
}
#up-down-caret-percentage-change {
    margin-top: 3px;
}

.up-down-caret-green {
    margin-left: 5px;
    color: green
}
.up-down-caret-red {
    margin-left: 5px;
    color: red;
}


  </style>