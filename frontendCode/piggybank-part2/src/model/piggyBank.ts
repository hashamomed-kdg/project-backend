import type {Owner} from "./owner.ts"
import type {Background} from "./background.ts"

export type PiggyBank = {
    id: string
    amount: number,
    background: Background,
    owner: Owner
}