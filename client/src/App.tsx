import React from "react";
import { ApolloProvider } from "react-apollo";
import ApolloClient from "apollo-boost";

import SprintPlanning from "./components/SprintPlanning";

// https://www.apollographql.com/docs/react/
const client = new ApolloClient();

const App: React.FC = () => {
  return (
    <ApolloProvider client={client}>
      <SprintPlanning />
    </ApolloProvider>
  );
};

export default App;
