# Teste técnico - iFood

### Overview
   Este projeto foi desenvolvido utilizando API do [Movie DB](https://developer.themoviedb.org/reference/intro/getting-started).<br/><br/>

   O aplicativo tem como objetivo listar filmes de diversas categorias e apresentar detalhes como data de lançamento, avaliação, entre outros. Além disso, também é possível buscar um filme pelo nome para ver os detalhes do mesmo,
   assim como encontrar títulos semelhantes ao filme selecionado.
   
   Para que as chamadas da API sejam autenticadas é necessário passar um Token no header da requisição, mas para facilitar no momento de rodar o app, há um token válido declarado no projeto. 
   Portanto, não é necessário gerar um novo token para que as requests executem com sucesso. <br/><br/>

### Como rodar os testes
   Para rodar os testes nesse projeto basta executar a seguinte task: <br/> <br/>
  ```bash
  gradle testDebugUnitTest
```

### Pontos de melhoria:
- Refatorar aplicação para que a UI seja desenvolvida com Jetpack Compose
- Adicionar testes de UI
   
