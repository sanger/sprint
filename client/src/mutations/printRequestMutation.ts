import { gql } from "apollo-boost";

export default gql`
  mutation Print($printRequest: PrintRequest!, $printer: String!) {
    print(printRequest: $printRequest, printer: $printer) {
      jobId
    }
  }
`;
