<template>
    <div class="chart-container">
    <h1 v-on:click="showChart = true; loaded = false" v-show="showChart == false">Show Commodities Charts <i class="fa-solid fa-caret-right"></i></h1>
    <h1 v-on:click="showChart = false; loaded = false" v-show="showChart == true">Hide Commodities Charts <i class="fa-solid fa-caret-down"></i></h1>
        
    <div class = "commodity-radio-buttons" v-show="showChart == true">
            <input type="radio" id="copper" name="commodities" value="copper" v-on:click="selectedCommodity = 'copper'">
            <label for="copper">Copper</label><br>
            <input type="radio" id="aluminum" name="commodities" value="aluminum" v-on:click="selectedCommodity = 'aluminum'">
            <label for="aluminum">Aluminum</label><br>
            <input type="radio" id="wheat" name="commodities" value="wheat" v-on:click="selectedCommodity = 'wheat'">
            <label for="javascript">Wheat</label>
            <input type="radio" id="corn" name="commodities" value="corn" v-on:click="selectedCommodity = 'corn'">
            <label for="javascript">Corn</label>
            <input type="radio" id="cotton" name="commodities" value="cotton" v-on:click="selectedCommodity = 'cotton'">
            <label for="javascript">Cotton</label>
            <input type="radio" id="sugar" name="commodities" value="sugar" v-on:click="selectedCommodity = 'sugar'">
            <label for="javascript">Sugar</label>
            <input type="radio" id="coffee" name="commodities" value="coffee" v-on:click="selectedCommodity = 'coffee'">
            <label for="javascript">Coffee</label>
            <input type="radio" id="globalIndex" name="commodities" value="globalIndex" v-on:click="selectedCommodity = 'globalIndex'">
            <label for="javascript">Global Index</label>
        </div>
        <div class = "commodity-radio-buttons" v-show="showChart == true">
            <input type="radio" id="monthly" name="time" value="monthly" v-on:click="loaded = false; timePeriod = 'monthly'; clearArray(); addData()">
            <label for="monthly">Monthly</label><br>
            <input type="radio" id="quarterly" name="time" value="quarterly" v-on:click="loaded = false; timePeriod = 'quarterly'; clearArray(); addData()">
            <label for="aluminum">Quarterly</label><br>
            <input type="radio" id="annually" name="time" value="annually" v-on:click="loaded = false; timePeriod = 'annually'; clearArray(); addData()">
            <label for="annually">Annually</label><br>
        </div>
        <div class = "commodity-radio-buttons" v-show="showChart == true">
            <input type="button" id="annually" name="time" value="Reset" v-on:click="loaded = false; timePeriod = 'annually'; clearArray()" style="border-radius: 20%; background-color: #3b9aed; color: #fff; ">
            <label for="annually"></label><br>
        </div>
        
    <div class="line-chart" v-show="loaded == true">
    <Line
      id="CommodityLineChart"
      ref="CommodityLineChart"
      :options="chartOptions"
      :data="chartData"
      v-if="loaded"
    />
</div>
</div>
  </template>
  
  <script>
  import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js'
import { Line } from 'vue-chartjs'
import CommoditiesService from '@/services/CommodityService'

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
)
  
  export default {
    name: 'CommoditiesChart',
    components: { Line },
    data() {
      return {
        selectedCommodity: '',
        timePeriod: '',
        loaded: false,
        aluminumAnnually: [],
        aluminumMonthly: [],
        aluminumQuarterly: [],
        copperAnnually: [],
        copperMonthly: [],
        copperQuarterly: [],
        wheatAnnually: [],
        wheatMonthly: [],
        wheatQuarterly: [],
        cornAnnually: [],
        cornMonthly: [],
        cornQuarterly: [],
        cottonAnnually: [],
        cottonMonthly: [],
        cottonQuarterly: [],
        sugarAnnually: [],
        sugarMonthly: [],
        sugarQuarterly: [],
        coffeeAnnually: [],
        coffeeMonthly: [],
        coffeeQuarterly: [],
        allCommoditiesAnnually: [],
        allCommoditiesMonthly: [],
        allCommoditiesQuarterly: [],
        showChart: false,
        chartData: {
            labels: [],
  datasets: [
    {
      label: 'Data One',
      backgroundColor: '#f87979',
      data: [],
      yAxisID: 'y',
    },
  ]
        },
        chartOptions: {
            responsive: true,
            maintainAspectRatio: false, // allow height and width to be set
        }
      }
    },
    methods: {
        clearArray() {
            while (this.chartData.labels.length > 0) {
                this.chartData.labels.pop();
        }
        while (this.chartData.datasets[0].data.length > 0) {
            this.chartData.datasets[0].data.pop();
        }
        },
        addData() {
            if(this.selectedCommodity == 'copper'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.copperMonthly.length; i++) {
                        this.chartData.labels.push(this.copperMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.copperMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.copperQuarterly.length; i++) {
                        this.chartData.labels.push(this.copperQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.copperQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.copperAnnually.length; i++) {
                this.chartData.labels.push(this.copperAnnually[i].date.toString())
                        this.chartData.datasets[0].data.push(this.copperAnnually[i].value)
                }
        }
    }
    if(this.selectedCommodity == 'aluminum'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.aluminumMonthly.length; i++) {
                        this.chartData.labels.push(this.aluminumMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.aluminumMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.aluminumQuarterly.length; i++) {
                        this.chartData.labels.push(this.aluminumQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.aluminumQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.aluminumAnnually.length; i++) {
                this.chartData.labels.push(this.aluminumAnnually[i].date.toString())
                        this.chartData.datasets[0].data.push(this.aluminumAnnually[i].value)
                }
        }
    }
    if(this.selectedCommodity == 'wheat'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.wheatMonthly.length; i++) {
                        this.chartData.labels.push(this.wheatMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.wheatMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.wheatQuarterly.length; i++) {
                        this.chartData.labels.push(this.wheatQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.wheatQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.wheatAnnually.length; i++) {
                this.chartData.labels.push(this.wheatAnnually[i].date.toString())
                        this.chartData.datasets[0].data.push(this.wheatAnnually[i].value)
                }
        }
    }
    if(this.selectedCommodity == 'corn'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.cornMonthly.length; i++) {
                        this.chartData.labels.push(this.cornMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.cornMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.cornQuarterly.length; i++) {
                        this.chartData.labels.push(this.cornQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.cornQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.cornAnnually.length; i++) {
                this.chartData.labels.push(this.cornAnnually[i].date.toString())
                        this.chartData.datasets[0].data.push(this.cornAnnually[i].value)
                }
        }
    }
    if(this.selectedCommodity == 'cotton'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.cottonMonthly.length; i++) {
                        this.chartData.labels.push(this.cottonMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.cottonMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.cottonQuarterly.length; i++) {
                        this.chartData.labels.push(this.cottonQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.cottonQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.cottonQuarterly.length; i++) {
                this.chartData.labels.push(this.cottonQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.cottonQuarterly[i].value)
                }
        }
    }
    if(this.selectedCommodity == 'sugar'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.sugarMonthly.length; i++) {
                        this.chartData.labels.push(this.sugarMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.sugarMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.sugarQuarterly.length; i++) {
                        this.chartData.labels.push(this.sugarQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.sugarQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.sugarAnnually.length; i++) {
                this.chartData.labels.push(this.sugarAnnually[i].date.toString())
                        this.chartData.datasets[0].data.push(this.sugarAnnually[i].value)
                }
        }
    }
    if(this.selectedCommodity == 'coffee'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.coffeeMonthly.length; i++) {
                        this.chartData.labels.push(this.coffeeMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.coffeeMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.coffeeQuarterly.length; i++) {
                        this.chartData.labels.push(this.coffeeQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.coffeeQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.coffeeAnnually.length; i++) {
                this.chartData.labels.push(this.coffeeAnnually[i].date.toString())
                        this.chartData.datasets[0].data.push(this.coffeeAnnually[i].value)
                }
        }
    }
    if(this.selectedCommodity == 'globalIndex'){
            if (this.timePeriod == 'monthly') {
                for (let i = 0; i < this.allCommoditiesMonthly.length; i++) {
                        this.chartData.labels.push(this.allCommoditiesMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.allCommoditiesMonthly[i].value)
            }
                } else if (this.timePeriod == 'quarterly') {
                    for (let i = 0; i < this.allCommoditiesQuarterly.length; i++) {
                        this.chartData.labels.push(this.allCommoditiesQuarterly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.allCommoditiesQuarterly[i].value)
            } 
        } else if (this.timePeriod == 'annually') {
            for (let i = 0; i < this.allCommoditiesAnnually.length; i++) {
                this.chartData.labels.push(this.allCommoditiesAnnually[i].date.toString())
                        this.chartData.datasets[0].data.push(this.allCommoditiesAnnually[i].value)
                }
        }
    }

    this.loaded = true

    }
    },
    async mounted() {
        CommoditiesService.listAluminumAnnually().then(response => {
      this.aluminumAnnually = response.data;
    }),
    CommoditiesService.listAluminumMonthly().then(response => {
      this.aluminumMonthly = response.data;
    }),
    CommoditiesService.listAluminumQuarterly().then(response => {
      this.aluminumQuarterly = response.data;
    }),
    CommoditiesService.listCopperAnnually().then(response => {
      this.copperAnnually = response.data;
    }),
    CommoditiesService.listCopperMonthly().then(response => {
      this.copperMonthly = response.data;
    }),
    CommoditiesService.listCopperQuarterly().then(response => {
      this.copperQuarterly = response.data;
    }),
    CommoditiesService.listWheatAnnually().then(response => {
      this.wheatAnnually = response.data;
    }),
    CommoditiesService.listWheatMonthly().then(response => {
      this.wheatMonthly = response.data;
    }),
    CommoditiesService.listWheatQuarterly().then(response => {
      this.wheatQuarterly = response.data;
    }),
    CommoditiesService.listCornAnnually().then(response => {
      this.cornAnnually = response.data;
    }),
    CommoditiesService.listCornMonthly().then(response => {
      this.cornMonthly = response.data;
    }),
    CommoditiesService.listCornQuarterly().then(response => {
      this.cornQuarterly = response.data;
    }),
    CommoditiesService.listCottonAnnually().then(response => {
      this.cottonAnnually = response.data;
    }),
    CommoditiesService.listCottonMonthly().then(response => {
      this.cottonMonthly = response.data;
    }),
    CommoditiesService.listCottonQuarterly().then(response => {
      this.cottonQuarterly = response.data;
    }),
    CommoditiesService.listSugarAnnually().then(response => {
      this.sugarAnnually = response.data;
    }),
    CommoditiesService.listSugarMonthly().then(response => {
      this.sugarMonthly = response.data;
    }),
    CommoditiesService.listSugarQuarterly().then(response => {
      this.sugarQuarterly = response.data;
    }),
    CommoditiesService.listCoffeeAnnually().then(response => {
      this.coffeeAnnually = response.data;
    }),
    CommoditiesService.listCoffeeMonthly().then(response => {
      this.coffeeMonthly = response.data;
    }),
    CommoditiesService.listCoffeeQuarterly().then(response => {
      this.coffeeQuarterly = response.data;
    }),
    CommoditiesService.listCommodityIndexAnnually().then(response => {
      this.allCommoditiesAnnually = response.data;
    }),
    CommoditiesService.listCommodityIndexMonthly().then(response => {
      this.allCommoditiesMonthly = response.data;
    }),
    CommoditiesService.listCommodityIndexQuarterly().then(response => {
      this.allCommoditiesQuarterly = response.data;
    })
    },
  }
  </script>

  <style scoped>

  .chart-container{
    display: flex;
    flex-direction: column;
    width: 100%;
    
  }
  .line-chart {
  height: 800px;
  width: 80%;
  display: flex;
  justify-content: center;
  margin: auto;
}

.commodity-radio-buttons{
    display: flex;
    justify-content: center;
}

@media (max-width: 916px){
  .commodity-radio-buttons{
    font-size: large;
}
.chart-container{
  font-size: x-small;
}
}

  </style>