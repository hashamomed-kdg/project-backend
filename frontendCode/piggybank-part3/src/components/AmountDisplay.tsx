import {Stack, Typography} from "@mui/material"

interface AmountDisplayProps {
    amount: number;
    currency: string;
}

export function AmountDisplay({amount, currency}: AmountDisplayProps) {
    return (
        <Stack direction="row" sx={{alignItems: 'center'}}>
            <Typography variant="h4" sx={{color: 'text.primary'}}>
                {amount}
            </Typography>
            <Typography variant="h6" sx={{color: 'text.secondary'}}>
                {currency}
            </Typography>
        </Stack>
    )
}
