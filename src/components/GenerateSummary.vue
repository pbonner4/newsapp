<template>
  <div>
    <div class="summary-container"> 
        <div class="summary"> 
            <p>Dont have time to read?</p>
            <button v-on:click="getSummary(article)">Get Summary</button>
            <h1>{{ typedSummary }}</h1>
        </div>
    </div> 
   

  </div>
</template>

<script>

import ArticleService from '@/services/ArticleService';
import OpenAiService from '@/services/OpenAiService'

export default {
    data(){
        return {
            article: '',
            summary: '',
            typedSummary: '',
            index: 0
        }
    },
    async mounted(){
        ArticleService.getArticleContent(this.$route.params.id).then(response => {
        this.article = response.data
      });
      this.typeSummary()
    },
    methods: {
      typeSummary(){
        setInterval(() => {
        if(this.summary.length > 0){
          if(this.index < this.summary.length){
            this.typedSummary += this.summary[this.index]
            this.index++
          }
        }
      }, 25)
    },
    async getSummary(article) {
        console.log(article)
        console.log(` article: \n title: ${article[0].title} \n content: ${article[0].text} \n link: ${article[0].url}`)
    

     let context = 'firstly, DO NOT PROVIDE ANY HTML ELEMENTS OR TAGS in your summary, but Can you generate an easy, digestible, informative, very serious toned summary of the provided article here:\n';

context += ` article: \n title: ${article[0].title} \n content: ${article[0].text.slice(0,2500)} \n link: ${article[0].url}`;



    let data = {
      model: 'text-davinci-003',
      prompt: context
    };

    let response = await OpenAiService.generateSummary(data);
    console.log(response)

    this.summary = response;
  }
    }

}
</script>

<style scoped>

.summary-container{
    padding: 4px;
    margin: 50px;
    background-color: rgb(0, 78, 161);
    width: 100%;
    text-align: center;
    border-radius: 10%; 
    font-family: Arial, Helvetica, sans-serif;
    color:  #fff;
}

</style>