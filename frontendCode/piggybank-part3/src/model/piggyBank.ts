import type {Owner} from "./owner.ts"
import type {Background} from "./background.ts"
import type {Donation} from "./donation.ts"

export type PiggyBank = {
    id: string
    amount: number,
    background: Background,
    owner: Owner
    donations: Donation[]
}


