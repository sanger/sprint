import { gql } from "apollo-boost";

export default gql`
  query LabelTypes {
    labelTypes {
      name
      width
      height
      displacement
      printers {
        hostname
      }
    }
  }
`;
