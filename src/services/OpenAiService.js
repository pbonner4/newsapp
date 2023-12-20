import axios from 'axios';

const openaiService = {

  async generateText(prompt) {

    let token = process.env.VUE_APP_OPEN_AI_KEY

    const response = await axios.post(
      'https://api.openai.com/v1/engines/davinci/completions',
      {
        prompt: prompt.messages,
        max_tokens: 100,
      },
      {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token,
        },
      }
    );

    return response.data.choices[0].text;
  },

  async generateEmbeddings(input, model) {
    let token = process.env.VUE_APP_OPEN_AI_KEY
    const response = await axios.post(
      'https://api.openai.com/v1/embeddings', 
      {
        model,
        input,
      },
      {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token,
        },
      }
    );

    return response.data;
  },

  async generateSummary(prompt) {

    let token = process.env.VUE_APP_OPEN_AI_KEY

    const response = await axios.post(
      'https://api.openai.com/v1/completions',
      {
        model: prompt.model,
        prompt: prompt.prompt,
        temperature: 1,
        max_tokens: 1596,
        top_p: 1.0,
        frequency_penalty: 0.0,
        presence_penalty: 1,
      },
      {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token,
        },
      }
    );
    return response.data.choices[0].text;
  },

};

export default openaiService;
