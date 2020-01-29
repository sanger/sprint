import React from "react";
import { ApolloProvider } from "@apollo/react-hooks";
import ApolloClient from "apollo-boost";

import SprintPlanning from "./components/SprintPlanning";

const client = new ApolloClient();

const App: React.FC = () => {
  return (
    <ApolloProvider client={client}>
      <SprintPlanning />
    </ApolloProvider>
  );
};

export default App;
