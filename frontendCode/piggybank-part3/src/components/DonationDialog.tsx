import {Dialog} from "@mui/material"
import {DonationForm} from "./DonationForm.tsx"
import type {PiggyBank} from "../model/piggyBank.ts"
import {useAddDonation} from "../hooks/usePiggyBanks.ts"
import type {DonationFormValues} from "../model/donation.ts"


interface DonationDialogProps {
    piggyBank: PiggyBank,
    isOpen: boolean
    onClose: () => void
}

const hardCodedSponsor = {
    id: '6',
    name: 'Grandma',
}

export function DonationDialog({piggyBank, isOpen, onClose}: DonationDialogProps) {
    const {isPending, isError, addNewDonation} = useAddDonation(piggyBank.id)

    function handleSumbit(donation: DonationFormValues) {
        addNewDonation({
            ...donation,
            piggybankId: piggyBank.id,
            sponsor: hardCodedSponsor,
            timestamp: new Date()
        })
        onClose()
    }

    if (isPending) {
        return <div>Creating donation...</div>
    }
    if (isError) {
        return <div>Error creating donation!</div>
    }

    return (
        <Dialog open={isOpen} onClose={onClose}>
            <DonationForm onSubmit={handleSumbit}/>
        </Dialog>
    )
}
