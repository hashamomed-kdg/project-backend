import {usePiggyBank, useUpdatePiggyBank} from "../hooks/usePiggyBanks.ts"
import {useParams} from "react-router"
import {PiggyBankView} from "../components/PiggyBankView.tsx"
import {DonationsList} from "../components/DonationsList.tsx"
import {Box} from "@mui/material"


export function PiggyBankDetailPage() {
    const {id} = useParams<{ id: string }>()
    const {piggyBank, isLoading, isError: isErrorLoading} = usePiggyBank(id!)
    const {updatePiggyBankMutation, isPending, isError: isErrorUpdating} = useUpdatePiggyBank()


    if (isLoading || isPending) {
        return <div>Operation in progress...</div>
    }

    if (isErrorLoading || isErrorUpdating || !piggyBank) {
        return <div>Error!</div>
    }

    return (
        <Box
            sx={{
                mt: 5,
                display: 'flex',
                flexWrap: 'wrap',
                alignItems: 'start',
                justifyContent: 'center',
                gap: 2
            }}
        >
            <PiggyBankView piggyBank={piggyBank} onBackgroundUpdated={(background) => {
                updatePiggyBankMutation({...piggyBank, background: background})
            }}/>
            <DonationsList piggyBank={piggyBank}/>
        </Box>
    )
}