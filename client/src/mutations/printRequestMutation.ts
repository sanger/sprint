import { gql } from "apollo-boost";

export default gql`
  mutation PrintRequest($printRequest: PrintRequest!, $printer: String!) {
    print(printRequest: $printRequest, printer: $printer) {
      jobId
    }
  }
`;
