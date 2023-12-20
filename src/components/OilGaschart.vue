<template>
    <div class="chart-container">
    <h1 v-on:click="showChart = true; loaded = false" v-show="showChart == false">Show Oil and Gas Charts <i class="fa-solid fa-caret-right"></i></h1>
    <h1 v-on:click="showChart = false; loaded = false" v-show="showChart == true">Hide Oil and Gas Charts <i class="fa-solid fa-caret-down"></i></h1>
        
    <div class = "commodity-radio-buttons" v-show="showChart == true">
            <input type="radio" id="oil" name="oilGas" value="brent" v-on:click="selectedCommodity = 'brent'">
            <label for="brent">Oil (BRENT)</label><br>
            <input type="radio" id="oil" name="oilGas" value="wti" v-on:click="selectedCommodity = 'wti'">
            <label for="qti">Oil (WTI)</label><br>
            <input type="radio" id="gas" name="oilGas" value="gas" v-on:click="selectedCommodity = 'natural_gas'">
            <label for="gas">Natural Gas</label>
        </div>
        <div class = "commodity-radio-buttons" v-show="showChart == true">
            <input type="radio" id="daily" name="time" value="daily" v-on:click="loaded = false; timePeriod = 'daily'; addData()">
            <label for="daily">Daily</label><br>
            <input type="radio" id="weekly" name="time" value="weekly" v-on:click="loaded = false; timePeriod = 'weekly'; addData()">
            <label for="weekly">Weekly</label><br>
            <input type="radio" id="monthly" name="time" value="monthly" v-on:click="loaded = false; timePeriod = 'monthly'; addData()">
            <label for="monthly">Monthly</label><br>
        </div>
        <div class = "commodity-radio-buttons" v-show="showChart == true">
            <input type="button" id="annually" name="time" value="Reset" v-on:click="loaded = false; timePeriod = 'annually'; clearArray()" style="border-radius: 20%; background-color: #3b9aed; color: #fff; ">
            <label for="annually"></label><br>
        </div>
        
    <div class="line-chart" v-show="loaded == true">
    <Line
      id="OilGasLineChart"
      ref="OilGasLineChart"
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
    name: 'OilGasChart',
    components: { Line },
    data() {
      return {
        selectedCommodity: '',
        timePeriod: '',
        loaded: false,
        brentDaily: [],
        brentWeekly: [],
        brentMonthly: [],
        wtiDaily: [],
        wtiWeekly: [],
        wtiMonthly: [],
        naturalGasDaily: [],
        naturalGasWeekly: [],
        naturalGasMonthly: [],
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
            location.reload()
        },
        addData() {
            if(this.selectedCommodity == 'wti'){
            if (this.timePeriod == 'daily') {
                for (let i = 0; i < this.wtiDaily.length; i++) {
                        this.chartData.labels.push(this.wtiDaily[i].date.toString())
                        this.chartData.datasets[0].data.push(this.wtiDaily[i].value)
            }
                } else if (this.timePeriod == 'weekly') {
                    for (let i = 0; i < this.wtiWeekly.length; i++) {
                        this.chartData.labels.push(this.wtiWeekly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.wtiWeekly[i].value)
            } 
        } else if (this.timePeriod == 'monthly') {
            for (let i = 0; i < this.wtiMonthly.length; i++) {
                this.chartData.labels.push(this.wtiMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.wtiMonthly[i].value)
                }
        }
    }
    else if(this.selectedCommodity == 'brent'){
            if (this.timePeriod == 'daily') {
                for (let i = 0; i < this.brentDaily.length; i++) {
                        this.chartData.labels.push(this.brentDaily[i].date.toString())
                        this.chartData.datasets[0].data.push(this.brentDaily[i].value)
            }
                } else if (this.timePeriod == 'weekly') {
                    for (let i = 0; i < this.brentWeekly.length; i++) {
                        this.chartData.labels.push(this.brentWeekly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.brentWeekly[i].value)
            } 
        } else if (this.timePeriod == 'monthly') {
            for (let i = 0; i < this.brentMonthly.length; i++) {
                this.chartData.labels.push(this.brentMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.brentMonthly[i].value)
                }
        }
    }
    else if(this.selectedCommodity == 'natural_gas'){
            if (this.timePeriod == 'daily') {
                for (let i = 0; i < this.naturalGasDaily.length; i++) {
                        this.chartData.labels.push(this.naturalGasDaily[i].date.toString())
                        this.chartData.datasets[0].data.push(this.naturalGasDaily[i].value)
            }
                } else if (this.timePeriod == 'weekly') {
                    for (let i = 0; i < this.naturalGasWeekly.length; i++) {
                        this.chartData.labels.push(this.naturalGasWeekly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.naturalGasWeekly[i].value)
            } 
        } else if (this.timePeriod == 'monthly') {
            for (let i = 0; i < this.naturalGasMonthly.length; i++) {
                this.chartData.labels.push(this.naturalGasMonthly[i].date.toString())
                        this.chartData.datasets[0].data.push(this.naturalGasMonthly[i].value)
                }
        }
    }
    
    this.loaded = true

    }
    },
    async mounted() {
        CommoditiesService.listWtiDaily().then(response => {
      this.wtiDaily = response.data;
    }),
    CommoditiesService.listWtiMonthly().then(response => {
      this.wtiMonthly = response.data;
    }),
    CommoditiesService.listWtiWeekly().then(response => {
      this.witeWeekly = response.data;
    }),
    CommoditiesService.listBrentDaily().then(response => {
        this.brentDaily = response.data
    }),
    CommoditiesService.listBrentWeekly().then(response => {
        this.brentWeekly = response.data
    }),
    CommoditiesService.listBrentMonthly().then(response => {
        this.brentMonthly = response.data
    }),
    CommoditiesService.listNaturalGasDaily().then(response => {
        this.naturalGasDaily = response.data
    }),
    CommoditiesService.listNaturalGasWeekly().then(response => {
        this.naturalGasWeekly = response.data
    }),
    CommoditiesService.listNaturalGasWeekly().then(response => {
        this.naturalGasMonthly = response.data
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