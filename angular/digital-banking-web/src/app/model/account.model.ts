import {Operation} from "./operation.model";

export interface Account{
    id:            string
    balance:       number
    currentPage:   number
    totalPages:    number
    size:          number
    operationDTOS: Operation[];
}
