import './OwnerBadge.scss'
import type {Owner} from "../model/owner.ts"

interface OwnerBadgeProps {
    owner: Owner
    onClick: () => void
    size?: "small" | "large"
}

export function OwnerBadge({
                               owner,
                               onClick,
                               size = "large"
                           }: OwnerBadgeProps) {
    return (
        <div
            className={`owner-badge ${size}`}
            title={owner.name}
            onClick={onClick}
        >
            <img src={`/img/users/${owner.id}.png`} alt={owner.name}/>
        </div>
    )
}
