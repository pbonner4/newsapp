<template>
  <div>
    <div class="summary-container"> 
        <div class="summary"> 
            <p>Don't have time to read?</p>
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

context += ` article: \n title: ${article[0].title} \n content: ${article[0].text.slice} \n link: ${article[0].url}`;



    let data = {
      model: 'gpt-3.5-turbo-instruct',
      context: context
    };

    let response = await OpenAiService.generateSummary(data);
    console.log(response)

    this.summary = response;
  }
    }

}
</script>

<style scoped>

.cursor-blink {
  display: inline-block;
  width: 8px;
  height: 2px;
  background-color: #333;
  animation: blink-animation 1s steps(5, start) infinite;
}

@keyframes blink-animation {
  to {
    visibility: hidden;
  }
}

.summary-container {
    padding: 20px;
    margin: 50px auto;
    background-color: #f5f5f5;
    width: 80%;
    text-align: center;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.summary {
    margin-bottom: 20px;
}

button {
    background-color: #0078d4;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

h1 {
    color: #333;
    font-size: 24px;
}

</style>
