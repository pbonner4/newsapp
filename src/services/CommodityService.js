import axios from 'axios';
const currentToken = localStorage.getItem('token')
const apiClient =axios.create({
  baseURL: "http://localhost:9000",
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${currentToken}`
  }
});

export default {
    listAllStockIndexData(){
        return apiClient.get('/allWeeklyTickers')
    },
    listAllCommodityData(){
        return apiClient.get('/allCommodities')
    },
    listCopperAnnually(){
        return apiClient.get(`/copperAnnually`)
    },
    listCopperMonthly(){
        return apiClient.get(`/copperMonthly`)
    },
    listCopperQuarterly(){
        return apiClient.get('/copperQuarterly')
    },
    listAluminumAnnually(){
        return apiClient.get(`/aluminumAnnually`)
    },
    listAluminumMonthly(){
        return apiClient.get(`/aluminumMonthly`)
    },
    listAluminumQuarterly(){
        return apiClient.get('/aluminumQuarterly')
    },
    listWheatAnnually(){
        return apiClient.get(`/wheatAnnually`)
    },
    listWheatMonthly(){
        return apiClient.get(`/wheatMonthly`)
    },
    listWheatQuarterly(){
        return apiClient.get('/wheatQuarterly')
    },
    listCornAnnually(){
        return apiClient.get(`/cornAnnually`)
    },
    listCornMonthly(){
        return apiClient.get(`/cornMonthly`)
    },
    listCornQuarterly(){
        return apiClient.get('/cornQuarterly')
    },
    listCottonAnnually(){
        return apiClient.get(`/cottonAnnually`)
    },
    listCottonMonthly(){
        return apiClient.get(`/cottonMonthly`)
    },
    listCottonQuarterly(){
        return apiClient.get('/cottonQuarterly')
    },
    listSugarAnnually(){
        return apiClient.get(`/sugarAnnually`)
    },
    listSugarMonthly(){
        return apiClient.get(`/sugarMonthly`)
    },
    listSugarQuarterly(){
        return apiClient.get('/sugarQuarterly')
    },
    listCoffeeAnnually(){
        return apiClient.get(`/coffeeAnnually`)
    },
    listCoffeeMonthly(){
        return apiClient.get(`/coffeeMonthly`)
    },
    listCoffeeQuarterly(){
        return apiClient.get('/coffeeQuarterly')
    },
    listCommodityIndexAnnually(){
        return apiClient.get(`/commodityIndexAnnually`)
    },
    listCommodityIndexMonthly(){
        return apiClient.get(`/commodityIndexMonthly`)
    },
    listCommodityIndexQuarterly(){
        return apiClient.get('/commodityIndexQuarterly')
    },

    //WTI OIL, BRENT OIL, NATURAL GAS
    listWtiDaily(){
        return apiClient.get('/wtiDaily')
    },
    listWtiWeekly(){
        return apiClient.get('/wtiWeekly')
    },
    listWtiMonthly(){
        return apiClient.get('/wtiMonthly')
    },
    //BRENT OIL
    listBrentDaily(){
        return apiClient.get('/brentDaily')
    },
    listBrentWeekly(){
        return apiClient.get('/brentWeekly')
    },
    listBrentMonthly(){
        return apiClient.get('/brentMonthly')
    },
    //NATURAL GAS
    listNaturalGasDaily(){
        return apiClient.get('/naturalGasDaily')
    },
    listNaturalGasWeekly(){
        return apiClient.get('/naturalGasWeekly')
    },
    listNaturalGasMonthly(){
        return apiClient.get('/naturalGasMonthly')
    },
    listGeneralTickers(){
        return apiClient.get('/weeklyPercentageChanges')
    }
}