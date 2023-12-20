<template>
  <div>
    <div>
      <input type="text" v-model="input" placeholder="Ask a question" />
      <button @click="chat(input)">Ask</button>
    </div>
    <div>  
        <div v-if="chatAnswer">
      Generated Answer: {{ chatAnswer }}
    </div>
    </div>
  </div>
</template>

<script>

import OpenAiService from '../services/OpenAiService';
import ArticleService from '../services/ArticleService';
import CommodityService from '../services/CommodityService'


export default {
    data(){
        return { 
        input: '',
        articles: [],
        articleData: [],
        inTheaters: [],
        comingSoon: [],
        indexData: [],
        commodities: [],
        chatAnswer: ''
        }
    },
    async mounted() {
        await ArticleService.getAllArticles().then(response => {
        this.articles = response.data;
      }),
      await ArticleService.getAllArticleContent().then(response => {
        this.articleData = response.data
      }),
      await ArticleService.listComingSoon().then(response => {
        this.comingSoon = response.data
      }),
      await ArticleService.listIntheaters().then(response => {
        this.inTheaters = response.data
      }),
      await CommodityService.listAllCommodityData().then(response => {
        this.commodities = response.data
      }),
      await CommodityService.listAllStockIndexData().then(response => {
        this.indexData = response.data
      })
    },
    methods: {
  async chat(input) {
    let context = 'You are a User\n';

context += `User: ${input}`;

console.log('length: ' + context.length)


    let data = {
      model: 'gpt-3.5-turbo',
      messages: context
    };

    const response = await OpenAiService.generateText(data);

    let answer = response.choices[0].message.content;
    this.chatAnswer = answer;
  }
}

    }
</script>

<style>

</style>