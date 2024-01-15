# Desafio Thomas Greg & Sons

O aplicativo é um gerenciador de usuários, feito para o desafio proposto pela
Thomas Greg & Sons.

## Sumário

1. [Checklist de requisitos do aplicativo](#Checklist-de-requisitos-do-aplicativo)
2. [Bibliotecas usadas](#bibliotecas-usadas)
3. [Arquitetura](#arquitetura)
4. [Telas](#telas)

## Checklist de requisitos do aplicativo

- [x] Cadastro de usuário
- [x] Atualização de usuário
- [x] Remoção de usuário
- [x] Listagem de usuários
- [x] Filtro de usuários
- [x] Estrutura da entidade Usuário
- [x] Post de usuário para a API
- [x] Utilizar SQLite para armazenamento de dados

## Bibliotecas usadas

- Retrofit (https://square.github.io/retrofit/)
- Android Navigation (https://developer.android.com/guide/navigation/navigation-getting-started?hl=pt-br)

## Arquitetura

O aplicativo foi separado em 4 pastas diferentes: data, network, ui, utils.

- ### Data
  É onde se encontra toda a lógica referente ao banco de dados local do aplicativo,
  nessa parte do aplicativo é onde se encontra a configuração do banco de dados,
  suas entidades e os casos de usos.
- ### Network
  É onde se encontra as configurações para chamadas a API, a execução dessas chamadas se encontram
  nos casos de uso na camada Data.
- ### UI
  É onde se encontra toda a parte visual do aplicativo: Activities, Fragments, RecyclerViews e etc.
  É onde irá encontrar as telas do aplicativo.  
  O padrão arquitetural que escolhi para o desenvolvimento do aplicativo foi o MVVM, onde a activity
  tem uma ViewModel centralizada e os fragments consomem desse ViewModel.
  Cada tela tem uma pasta separada para si com View, ViewModel e Model, facilitando a separação de
  cada tela.
- ### Utils
  É onde se encontram as classes para utilidades gerais do aplicativo, são classes com metodos úteis
  para certas tarefas no aplicativo que não cabiam em um dos componentes da MVVM. São classes com
  metodos estáticos para que possam ser utulizados de forma prática por todo o aplicativo.

## Telas

O aplicativo foi separado em 3 telas: Main, Cadastro de usuário e Detalhes do usuário.

- ### Main
  A tela principal lista os usuários cadastrados, também tem a função de deletar usuários caso o
  símbolo de lixeira for clicado, e caso o card do usuário for clicado o usuário será direcionado
  para a tela de detalhes do usuário. Além disso, no canto inferior direito há o botão flutuante com
  o simbolo de mais (+), que direciona o usuário para a tela de cadastro de usuários.

- ### Cadastro de usuários
    - É uma tela dividida em 5 partes, primeiramente o usuário registra nome, nome de usuário e
      email.
    - Em seguida irá informar o endereço físico, a data de nascimento e informar o genêro.
    - A tela seguinte irá informar se é uma Pessoa física ou jurídica e registrar o número do
      documento.
    - Por fim irá cadastrar um senha para o registro.
    - Ao final, o aplicativo irá tentar fazer um Post para a API com os dados do usuário, caso seja
      bem sucedido, o aplicativo irá salvar esse registro também no banco de dados local e
      direcionar o usuário para uma tela que informa ao usuário que o cadastro foi realizado com
      sucesso, e ao confirmar o sucesso, o usuário é direcionado de volta para a tela principal com
      o novo registro já incluso.  
      NOTA: No metodo post, o usuário envia a imagem para a API como uma string Base64, mas como um
      Base64 gasta muito recurso do aplicativo e gera erro ao tentar guardar o código Base64 no
      banco de dados SQL, no banco de dados local a imagem é armazenada apenas como um caminho URI.
      O metodo de cadastro de usuário foi repartido em 5 partes pois são muitos dados que coletamos,
      poucas telas deixariam a experiência massante e custosa para o usuário, dessa forma foi
      escolhida um fluxo mais leva para o usuário.

- ### Detalhes do Usuário
  A tela de detalhes do usuário lista todos os atributos cadastrado do usuário selecionado, exceto a
  senha. Esssa tela além de listar todos os dados do usuário também possibilita a atualização desses
  dados amostra.
  Para mudar a senha do usuário, foi separado um botão no final da tela que direciona o usuário para
  a mudança da senha, onde o usuário terá que informar uma nova senha para ser registrada.
